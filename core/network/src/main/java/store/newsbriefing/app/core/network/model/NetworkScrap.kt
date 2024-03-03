package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkScrap(
    @SerializedName("briefingId")
    val briefingId: Int,
    @SerializedName("ranks")
    val ranks: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("gptModel")
    val gptModel: String,
    @SerializedName("timeOfDay")
    val timeOfDay: String,
)

data class NetworkScrapCreate(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("briefingId")
    val briefingId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
)

class NetworkScrapDelete(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String
)