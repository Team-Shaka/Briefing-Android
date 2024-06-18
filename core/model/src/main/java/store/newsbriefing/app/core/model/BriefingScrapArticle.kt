package store.newsbriefing.app.core.model

data class BriefingScrapArticle(
    val date: String,
    val scraps: List<Scrap>
)
