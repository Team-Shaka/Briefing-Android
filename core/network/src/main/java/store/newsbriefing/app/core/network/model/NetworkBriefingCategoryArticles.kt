package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkBriefingCategoryArticles(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("briefings")
    val briefings: List<NetworkBriefingArticleSummary>
)
