package store.newsbriefing.app.core.network.model.scrap

import com.google.gson.annotations.SerializedName

class NetworkScrapDelete(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String
)