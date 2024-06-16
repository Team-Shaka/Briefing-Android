package store.newsbriefing.app.core.network.retrofit.api

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import store.newsbriefing.app.core.network.datasource.ScrapNetworkDataSource
import store.newsbriefing.app.core.network.model.NetworkScrap
import store.newsbriefing.app.core.network.model.RetrofitCommonResponse
import javax.inject.Inject
import javax.inject.Singleton

private data class PostScrapRequest(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("briefingId")
    val briefingId: Long,
)

private data class ScrapCreateResponse(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("briefingId")
    val briefingId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
)

private class ScrapDeleteResponse(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String
)

private interface RetrofitScrapApi {
    @GET("scraps/briefings/members/{memberId}")
    suspend fun getScrap(
        @Path("memberId") memberId: Long,
    ): RetrofitCommonResponse<List<NetworkScrap>>

    @POST("scraps/briefings")
    suspend fun setScrap(
        @Body request: PostScrapRequest,
    ): RetrofitCommonResponse<ScrapCreateResponse>

    @DELETE("scraps/briefings/{briefingId}/members/{memberId}")
    suspend fun setUnScrap(
        @Path("briefingId") briefingId: Long,
        @Path("memberId") memberId: Long,
    ): RetrofitCommonResponse<ScrapDeleteResponse>
}

@Singleton
internal class RetrofitScrapNetwork @Inject constructor(
    private val retrofit: Retrofit
) : ScrapNetworkDataSource {
    private val api: RetrofitScrapApi by lazy {
        retrofit.create(RetrofitScrapApi::class.java)
    }

    override suspend fun getScrap(memberId: Long): List<NetworkScrap> {
        return api.getScrap(memberId).result
    }

    override suspend fun setScrap(memberId: Long, articleId: Long) {
        val response = api.setScrap(PostScrapRequest(memberId, articleId))
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
    }

    override suspend fun unScrap(memberId: Long, articleId: Long) {
        val response = api.setUnScrap(articleId, memberId)
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
    }
}