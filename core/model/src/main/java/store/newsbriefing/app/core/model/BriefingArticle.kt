package store.newsbriefing.app.core.model

import java.time.ZonedDateTime

data class BriefingArticle(
    val id: Long,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val content: String,
    val date: ZonedDateTime,
    val articles: List<BriefingArticleRelated>,
    val isScrap: Boolean,
    val isBriefingOpen: Boolean,
    val isWarning: Boolean,
    val scrapCount: Int,
    val gptModel: String,
    val timeOfDay: String,
    val type: String
)
