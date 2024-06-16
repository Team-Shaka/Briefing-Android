package store.newsbriefing.app.core.data.repository.model

import store.newsbriefing.app.core.common.util.toZoneDateTime
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.network.model.NetworkScrap

fun NetworkScrap.asExternalModel() = Scrap(
    briefingId = briefingId,
    ranks = ranks,
    title = title,
    subtitle = subtitle,
    date = date.toZoneDateTime(),
    gptModel = gptModel,
    timeOfDay = timeOfDay
)