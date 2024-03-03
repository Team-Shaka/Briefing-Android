package store.newsbriefing.app.core.network.model.briefing

import com.google.gson.annotations.SerializedName

data class NetworkBriefingArticleRelated(
    @SerializedName("id") val id: Int,
    @SerializedName("press") val press: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)