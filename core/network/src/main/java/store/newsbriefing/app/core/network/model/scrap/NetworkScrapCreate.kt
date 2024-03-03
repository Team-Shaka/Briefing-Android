package store.newsbriefing.app.core.network.model.scrap

import com.google.gson.annotations.SerializedName

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