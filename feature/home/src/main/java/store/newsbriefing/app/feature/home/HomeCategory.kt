package store.newsbriefing.app.feature.home

import store.newsbriefing.app.core.model.BriefingArticleCategory

enum class HomeCategory(
    val title: Int,
    val category: BriefingArticleCategory
) {
    SOCIETY(
        R.string.tab_society,
        BriefingArticleCategory.SOCIAL
    ),
    SCIENCE(
        R.string.tab_science,
        BriefingArticleCategory.SCIENCE
    ),
    GLOBAL(
        R.string.tab_global,
        BriefingArticleCategory.GLOBAL
    ),
    ECONOMY(
        R.string.tab_economy,
        BriefingArticleCategory.ECONOMY
    )
}