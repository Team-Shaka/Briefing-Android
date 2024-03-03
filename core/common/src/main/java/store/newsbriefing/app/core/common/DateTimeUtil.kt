package store.newsbriefing.app.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


fun String.toZoneDateTime(): ZonedDateTime {
    val instant = Instant.parse(this)
    return instant.atZone(ZoneId.systemDefault())
}