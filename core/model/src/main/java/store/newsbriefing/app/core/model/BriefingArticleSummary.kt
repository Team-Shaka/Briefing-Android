package store.newsbriefing.app.core.model

data class BriefingArticleSummary(
    val id: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val scrapCount: Int
)