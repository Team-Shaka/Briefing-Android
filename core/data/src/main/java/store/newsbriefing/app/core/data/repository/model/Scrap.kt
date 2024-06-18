package store.newsbriefing.app.core.data.repository.model

import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.model.TimeOfDay
import store.newsbriefing.app.core.network.model.NetworkScrap

fun NetworkScrap.asExternalModel() = Scrap(
    briefingId = briefingId,
    ranks = ranks,
    title = title,
    subtitle = subtitle,
    date = date,
    gptModel = gptModel,
    timeOfDay = TimeOfDay.fromValue(timeOfDay)
)