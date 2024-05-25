package store.newsbriefing.app.core.common.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale


fun String.toZoneDateTime(): ZonedDateTime {
    val instant = Instant.parse(this)
    return instant.atZone(ZoneId.systemDefault())
}

fun Date.toBriefingDate(): String {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN)
    val timeFormat = SimpleDateFormat("HH", Locale.KOREAN)

    val datePart = dateFormat.format(this)
    val hourPart = timeFormat.format(this).toInt()

    val briefingPart = if (hourPart < 16) "아침 브리핑" else "저녁 브리핑"

    return "$datePart $briefingPart"
}