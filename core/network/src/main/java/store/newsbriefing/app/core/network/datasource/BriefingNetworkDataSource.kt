package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.TimeOfDay
import store.newsbriefing.app.core.network.model.briefing.NetworkBriefingArticle
import store.newsbriefing.app.core.network.model.briefing.NetworkBriefingArticleSummary
import java.time.LocalDate

interface BriefingNetworkDataSource {
    suspend fun getBriefingArticleSummaries(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate?,
        timeOfDay: TimeOfDay?
    ): List<NetworkBriefingArticleSummary>

    suspend fun getBriefingArticle(articleId: Long): NetworkBriefingArticle
}