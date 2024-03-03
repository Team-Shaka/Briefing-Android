package store.newsbriefing.app.core.model

import java.time.ZonedDateTime

data class ScrapCreateResult(
    val scrapId: Int,
    val memberId: Int,
    val briefingId: Int,
    val createdAt: ZonedDateTime,
)