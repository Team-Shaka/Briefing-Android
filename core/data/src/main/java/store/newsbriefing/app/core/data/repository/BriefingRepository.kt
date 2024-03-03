package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingArticleSummary
import store.newsbriefing.app.core.model.TimeOfDay
import java.time.LocalDate

interface BriefingRepository {
    suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate?,
        timeOfDay: TimeOfDay?
    ): Flow<List<BriefingArticleSummary>>

    suspend fun getBriefingArticle(articleId: Long): Flow<BriefingArticle>
}