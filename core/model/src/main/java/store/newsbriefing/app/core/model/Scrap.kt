package store.newsbriefing.app.core.model

data class Scrap(
    val briefingId: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val date: String,
    val gptModel: String,
    val timeOfDay: String,
)