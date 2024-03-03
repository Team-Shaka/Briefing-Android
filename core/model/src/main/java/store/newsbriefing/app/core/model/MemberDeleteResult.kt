package store.newsbriefing.app.core.model

import java.time.ZonedDateTime

data class MemberDeleteResult(
    val quitAt: ZonedDateTime
)