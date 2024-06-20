package store.newsbriefing.app.core.network.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import store.newsbriefing.app.core.common.util.BriefingLogger
import store.newsbriefing.app.core.datastore.datasource.UserAuthTokenDataSource
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userAuthTokenDataSource: UserAuthTokenDataSource,
    private val memberNetworkDataSource: dagger.Lazy<MemberNetworkDataSource>
) : Interceptor {

    private val gson: Gson = Gson()
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token: String? = getSavedRefreshToken()

        val response = chain.proceed(
            originalRequest.newBuilder()
                .header("User-Agent", "Android")
                .header("content-type", "application/json")
                .apply {
                    if (token != null) {
                        header("Authorization", "Bearer $token")
                        BriefingLogger.d("request token : $token")
                    }
                }.build()
        )

        if (response.code == 200 || response.code == 201) {
            BriefingLogger.d(
                "response co : ${response.code} ${
                    response.peekBody(2048).string()
                }"
            )
        } else {
            BriefingLogger.e(
                "response code : ${response.code} ${
                    response.peekBody(2048).string()
                }"
            )
        }


        if (response.code == 401 && token != null) {
            val responseBodyString = response.peekBody(2048).string()

            val jsonObject = gson.fromJson(responseBodyString, JsonObject::class.java)
            val code = jsonObject["code"].asString

            if (code == "AUTH004") {
                BriefingLogger.d("request : $originalRequest")
                BriefingLogger.d("token expired")

                val newAccessToken = runBlocking { callRefreshTokenAPI() }
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private fun getSavedRefreshToken(): String? {
        return runBlocking {
            try {
                userAuthTokenDataSource.getUserAuthToken().first().accessToken
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun callRefreshTokenAPI(): String {
        return try {
            val userAuthToken =
                memberNetworkDataSource.get().getRefreshedAccessToken(getSavedRefreshToken()!!)

            userAuthTokenDataSource.saveUserAuthToken(
                userAuthToken.memberId,
                userAuthToken.accessToken,
                userAuthToken.refreshToken
            )

            return userAuthToken.refreshToken
        } catch (e: Exception) {
            userAuthTokenDataSource.clear()
            BriefingLogger.e("refresh token error : ${e.message}")
            ""
        }
    }

}