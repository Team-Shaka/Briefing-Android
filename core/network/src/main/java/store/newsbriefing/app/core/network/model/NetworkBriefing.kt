package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName
import store.newsbriefing.app.core.common.toZoneDateTime
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleRelated
import store.newsbriefing.app.core.model.BriefingArticleSummary

data class NetworkBriefingArticle(
    @SerializedName("id") val id: Long,
    @SerializedName("ranks") val ranks: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String,
    @SerializedName("articles") val articles: List<NetworkBriefingArticleRelated>,
    @SerializedName("isScrap") val isScrap: Boolean,
    @SerializedName("isBriefingOpen") val isBriefingOpen: Boolean,
    @SerializedName("isWarning") val isWarning: Boolean,
    @SerializedName("scrapCount") val scrapCount: Int,
    @SerializedName("gptModel") val gptModel: String,
    @SerializedName("timeOfDay") val timeOfDay: String,
    @SerializedName("type") val type: String
)

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

data class NetworkBriefingArticleRelated(
    @SerializedName("id") val id: Int,
    @SerializedName("press") val press: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)

fun NetworkBriefingArticleRelated.asExternalModel(): BriefingArticleRelated {
    return BriefingArticleRelated(
        id = id,
        press = press,
        title = title,
        url = url
    )
}

data class NetworkBriefingArticleSummary(
    @SerializedName("id") val id: Int,
    @SerializedName("ranks") val ranks: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("scrapCount") val scrapCount: Int
)

fun NetworkBriefingArticleSummary.asExternalModel(): BriefingArticleSummary {
    return BriefingArticleSummary(
        id = id,
        ranks = ranks,
        title = title,
        subtitle = subtitle,
        scrapCount = scrapCount
    )
}