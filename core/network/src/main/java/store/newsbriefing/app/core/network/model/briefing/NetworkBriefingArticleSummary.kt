package store.newsbriefing.app.core.network.model.briefing

import com.google.gson.annotations.SerializedName

data class NetworkBriefingArticleSummary(
    @SerializedName("id") val id: Int,
    @SerializedName("ranks") val ranks: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("scrapCount") val scrapCount: Int
) {}