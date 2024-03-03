package store.newsbriefing.app.core.network.retrofit.api

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.model.member.NetworkMemberDelete
import store.newsbriefing.app.core.network.model.member.NetworkMemberToken
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
        @Query("provider") provider: String,
        @Body request: PostTokenWithSocialProviderRequest
    ): RetrofitCommonResponse<NetworkMemberToken>

    @POST("members/auth/token")
    suspend fun postTokenWithRefreshToken(
        @Body request: PostTokenWithRefreshTokenRequest
    ): RetrofitCommonResponse<NetworkMemberToken>

    @DELETE("members/{memberId}")
    suspend fun deleteMember(
        @Path("memberId") memberId: Long
    ): RetrofitCommonResponse<NetworkMemberDelete>
}

@Singleton
internal class RetrofitMemberNetwork @Inject constructor(
    private val retrofit: Retrofit
) : MemberNetworkDataSource {
    private val api: RetrofitMemberApi by lazy {
        retrofit.create(RetrofitMemberApi::class.java)
    }

    override suspend fun deleteMember(memberId: Long): NetworkMemberDelete {
        val response = api.deleteMember(memberId)
        return response.result
    }

    override suspend fun getTokenWithSocialProvider(
        provider: String,
        identityToken: String
    ): NetworkMemberToken {
        val response = api.postTokenWithSocialProvider(provider, PostTokenWithSocialProviderRequest(identityToken))
        return response.result
    }

    override suspend fun getRefreshedAccessToken(refreshToken: String): NetworkMemberToken {
        val response = api.postTokenWithRefreshToken(PostTokenWithRefreshTokenRequest(refreshToken))
        return response.result
    }
}