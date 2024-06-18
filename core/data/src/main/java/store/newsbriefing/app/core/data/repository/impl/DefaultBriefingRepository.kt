package store.newsbriefing.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.data.repository.BriefingRepository
import store.newsbriefing.app.core.data.repository.model.asExternalModel
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingCategoryArticles
import store.newsbriefing.app.core.network.datasource.BriefingNetworkDataSource
import javax.inject.Inject

internal class DefaultBriefingRepository @Inject constructor(
    private val briefingNetworkDataSource: BriefingNetworkDataSource
) : BriefingRepository {
    override suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory,
    ): Flow<BriefingCategoryArticles> = flow {
        val summaries = briefingNetworkDataSource.getBriefingArticleSummaries(
            briefingArticleCategory,
            null,
            null
        )
        emit(summaries.asExternalModel())
    }

    override suspend fun getBriefingArticle(articleId: Long): Flow<BriefingArticle> {
        return flow {
            emit(briefingNetworkDataSource.getBriefingArticle(articleId).asExternalModel())
        }
    }
}