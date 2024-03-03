package store.newsbriefing.app.core.network.retrofit.api

import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.TimeOfDay
import store.newsbriefing.app.core.network.datasource.BriefingNetworkDataSource
import store.newsbriefing.app.core.network.model.RetrofitCommonResponse
import store.newsbriefing.app.core.network.model.briefing.NetworkBriefingArticle
import store.newsbriefing.app.core.network.model.briefing.NetworkBriefingArticleSummary
import store.newsbriefing.app.core.network.model.member.NetworkMemberDelete
import store.newsbriefing.app.core.network.model.member.NetworkMemberToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitBriefingApi {
    @GET("briefings")
    suspend fun getBriefingArticleSummaries(
        @Query("type") type: String,
        @Query("date") date: String?,
        @Query("timeOfDay") timeOfDay: String?
    ): RetrofitCommonResponse<List<NetworkBriefingArticleSummary>>

    @GET("briefings/{id}")
    suspend fun getBriefingArticle(@Path("id") id: Long): RetrofitCommonResponse<NetworkBriefingArticle>
}

@Singleton
internal class RetrofitBriefingNetwork @Inject constructor(
    private val retrofit: Retrofit
) : BriefingNetworkDataSource {
    private val api: RetrofitBriefingApi by lazy {
        retrofit.create(RetrofitBriefingApi::class.java)
    }

    override suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate?,
        timeOfDay: TimeOfDay?
    ): List<NetworkBriefingArticleSummary> {
        return api.getBriefingArticleSummaries(
            type = briefingArticleCategory.typeId,
            date = dateLocalDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            timeOfDay = timeOfDay?.value
        ).result
    }

    override suspend fun getBriefingArticle(articleId: Long): NetworkBriefingArticle {
        return api.getBriefingArticle(articleId).result
    }
}