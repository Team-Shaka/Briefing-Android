package store.newsbriefing.app.core.data.repository.model

import store.newsbriefing.app.core.common.util.toZoneDateTime
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleRelated
import store.newsbriefing.app.core.model.BriefingArticleSummary
import store.newsbriefing.app.core.network.model.NetworkBriefingArticle
import store.newsbriefing.app.core.network.model.NetworkBriefingArticleRelated
import store.newsbriefing.app.core.network.model.NetworkBriefingArticleSummary

fun NetworkBriefingArticle.asExternalModel(): BriefingArticle {
    return BriefingArticle(
        id = id,
        ranks = ranks,
        title = title,
        subtitle = subtitle,
        content = content,
        date = date.toZoneDateTime(),
        articles = articles.map { it.asExternalModel() },
        isScrap = isScrap,
        isBriefingOpen = isBriefingOpen,
        isWarning = isWarning,
        scrapCount = scrapCount,
        gptModel = gptModel,
        timeOfDay = timeOfDay,
        type = type
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