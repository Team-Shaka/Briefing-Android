package store.newsbriefing.app.core.network.model.briefing

import com.google.gson.annotations.SerializedName

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