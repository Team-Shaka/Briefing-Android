package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingCategoryArticles

interface BriefingRepository {
    suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory
    ): Flow<BriefingCategoryArticles>

    suspend fun getBriefingArticle(articleId: Long): Flow<BriefingArticle>
}