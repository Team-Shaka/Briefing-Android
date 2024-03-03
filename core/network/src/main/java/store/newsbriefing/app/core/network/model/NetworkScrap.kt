package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName
import store.newsbriefing.app.core.common.toZoneDateTime
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.model.ScrapCreateResult
import store.newsbriefing.app.core.model.ScrapDeleteResult

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

fun NetworkScrap.asExternalModel() = Scrap(
    briefingId = briefingId,
    ranks = ranks,
    title = title,
    subtitle = subtitle,
    date = date.toZoneDateTime(),
    gptModel = gptModel,
    timeOfDay = timeOfDay
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

fun NetworkScrapCreate.asExternalModel(): ScrapCreateResult {
    return ScrapCreateResult(
        scrapId = scrapId,
        memberId = memberId,
        briefingId = briefingId,
        createdAt = createdAt.toZoneDateTime()
    )
}

class NetworkScrapDelete(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String
)

fun NetworkScrapDelete.asExternalModel(): ScrapDeleteResult {
    return ScrapDeleteResult(
        scrapId = scrapId,
        deletedAt = deletedAt.toZoneDateTime()
    )
}