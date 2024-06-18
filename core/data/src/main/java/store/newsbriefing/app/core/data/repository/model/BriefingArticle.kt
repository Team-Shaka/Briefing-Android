package store.newsbriefing.app.core.data.repository.model

import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingArticleRelated
import store.newsbriefing.app.core.model.BriefingArticleSummary
import store.newsbriefing.app.core.model.BriefingCategoryArticles
import store.newsbriefing.app.core.model.TimeOfDay
import store.newsbriefing.app.core.network.model.NetworkBriefingArticle
import store.newsbriefing.app.core.network.model.NetworkBriefingArticleRelated
import store.newsbriefing.app.core.network.model.NetworkBriefingArticleSummary
import store.newsbriefing.app.core.network.model.NetworkBriefingCategoryArticles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NetworkBriefingCategoryArticles.asExternalModel(): BriefingCategoryArticles {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val parsedDate = dateFormat.parse(createdAt) ?: Date()

    return BriefingCategoryArticles(
        createdAt = parsedDate,
        briefings = briefings.map { it.asExternalModel() }
    )
}

fun NetworkBriefingArticle.asExternalModel(): BriefingArticle {
    return BriefingArticle(
        id = id,
        ranks = ranks,
        title = title,
        subtitle = subtitle,
        content = content,
        date = date,
        articles = articles.map { it.asExternalModel() },
        isScrap = isScrap,
        isBriefingOpen = isBriefingOpen,
        isWarning = isWarning,
        scrapCount = scrapCount,
        gptModel = gptModel,
        timeOfDay = TimeOfDay.fromValue(timeOfDay),
        category = BriefingArticleCategory.fromTypeName(type)
    )
}

fun NetworkBriefingArticleRelated.asExternalModel(): BriefingArticleRelated {
    return BriefingArticleRelated(
        id = id,
        press = press,
        title = title,
        url = url
    )
}

fun NetworkBriefingArticleSummary.asExternalModel(): BriefingArticleSummary {
    return BriefingArticleSummary(
        id = id,
        ranks = ranks,
        title = title,
        subtitle = subtitle,
        scrapCount = scrapCount
    )
}