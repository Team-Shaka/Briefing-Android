package store.newsbriefing.app.core.model

import java.time.ZonedDateTime

data class ScrapDeleteResult(
    val scrapId: Int,
    val deletedAt: ZonedDateTime
)
