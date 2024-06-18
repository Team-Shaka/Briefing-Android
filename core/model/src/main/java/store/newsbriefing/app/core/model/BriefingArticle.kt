package store.newsbriefing.app.core.model

data class BriefingArticle(
    val id: Long,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val content: String,
    val date: String,
    val articles: List<BriefingArticleRelated>,
    val isScrap: Boolean,
    val isBriefingOpen: Boolean,
    val isWarning: Boolean,
    val scrapCount: Int,
    val gptModel: String,
    val timeOfDay: TimeOfDay,
    val category: BriefingArticleCategory
)
