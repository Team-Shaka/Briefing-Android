package store.newsbriefing.app.core.network.retrofit.api

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import store.newsbriefing.app.core.model.SocialProvider
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.model.NetworkMemberDeleteResponse
import store.newsbriefing.app.core.network.model.NetworkMemberToken
import store.newsbriefing.app.core.network.model.RetrofitCommonResponse
import javax.inject.Inject
import javax.inject.Singleton

private data class PostTokenWithRefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)
private data class PostTokenWithSocialProviderRequest(
    @SerializedName("identityToken")
    val identityToken: String
)

private interface RetrofitMemberApi {
    @POST("members/auth/{provider}")
    suspend fun postTokenWithSocialProvider(
        @Path("provider") provider: String,
        @Body request: PostTokenWithSocialProviderRequest
    ): RetrofitCommonResponse<NetworkMemberToken>

    @POST("members/auth/token")
    suspend fun postTokenWithRefreshToken(
        @Body request: PostTokenWithRefreshTokenRequest
    ): RetrofitCommonResponse<NetworkMemberToken>

    @DELETE("members/{memberId}")
    suspend fun deleteMember(
        @Path("memberId") memberId: Long
    ): RetrofitCommonResponse<NetworkMemberDeleteResponse>
}

@Singleton
internal class RetrofitMemberNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) : MemberNetworkDataSource {
    private val api: RetrofitMemberApi by lazy {
        retrofit.create(RetrofitMemberApi::class.java)
    }

    override suspend fun deleteMember(memberId: Long) {
        val response = api.deleteMember(memberId)
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
    }

    override suspend fun getTokenWithSocialProvider(
        provider: SocialProvider,
        identityToken: String
    ): NetworkMemberToken {
        val response = api.postTokenWithSocialProvider(provider.value, PostTokenWithSocialProviderRequest(identityToken))
        return response.result
    }

    override suspend fun getRefreshedAccessToken(refreshToken: String): NetworkMemberToken {
        val response = api.postTokenWithRefreshToken(PostTokenWithRefreshTokenRequest(refreshToken))
        return response.result
    }
}