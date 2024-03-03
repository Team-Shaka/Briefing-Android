package store.newsbriefing.app.core.model

import java.time.ZonedDateTime

data class Scrap(
    val briefingId: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    val gptModel: String,
    val timeOfDay: String,
)