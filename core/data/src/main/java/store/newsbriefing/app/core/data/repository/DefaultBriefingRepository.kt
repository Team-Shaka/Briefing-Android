package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingArticleSummary
import store.newsbriefing.app.core.model.TimeOfDay
import store.newsbriefing.app.core.network.datasource.BriefingNetworkDataSource
import store.newsbriefing.app.core.network.model.asExternalModel
import java.time.LocalDate

internal class DefaultBriefingRepository(
    private val briefingNetworkDataSource: BriefingNetworkDataSource
) : BriefingRepository {
    override suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate?,
        timeOfDay: TimeOfDay?
    ): Flow<List<BriefingArticleSummary>> = flow {
        val summaries = briefingNetworkDataSource.getBriefingArticleSummaries(
            briefingArticleCategory,
            dateLocalDate,
            timeOfDay
        )
        emit(summaries.map {
            it.asExternalModel()
        })
    }

    override suspend fun getBriefingArticle(articleId: Long): Flow<BriefingArticle> {
        return flow {
            emit(briefingNetworkDataSource.getBriefingArticle(articleId).asExternalModel())
        }
    }
}