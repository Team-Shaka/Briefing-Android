package store.newsbriefing.app.core.model

enum class TimeOfDay(val value: String) {
    MORNING("Morning"),
    EVENING("Evening");

    companion object {
        fun fromValue(value: String): TimeOfDay {
            return values().firstOrNull { it.value.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid value for TimeOfDay: $value")
        }
    }
}