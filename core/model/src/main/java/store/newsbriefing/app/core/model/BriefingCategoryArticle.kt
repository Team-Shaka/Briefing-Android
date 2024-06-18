package store.newsbriefing.app.core.model

import java.util.Date

data class BriefingCategoryArticle(
    val createdAt: Date,
    val briefings: List<BriefingArticleSummary>
)
