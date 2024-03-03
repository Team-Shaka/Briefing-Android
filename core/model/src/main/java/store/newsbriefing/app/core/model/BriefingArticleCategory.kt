package store.newsbriefing.app.core.model
enum class BriefingArticleCategory(val typeId: String) {
    KOREA("KOREA"),
    GLOBAL("GLOBAL"),
    SOCIAL("SOCIAL"),
    SCIENCE("SCIENCE"),
    ECONOMY("ECONOMY");

    companion object {
        fun fromTypeName(typeName: String): BriefingArticleCategory {
            return values().firstOrNull { it.typeId.equals(typeName, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid typeName for ArticleType: $typeName")
        }
    }
}