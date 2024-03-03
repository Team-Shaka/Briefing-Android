package store.newsbriefing.app.core.network.model.scrap

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