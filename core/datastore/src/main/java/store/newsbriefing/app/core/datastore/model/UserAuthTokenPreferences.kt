package store.newsbriefing.app.core.datastore.model

data class UserAuthTokenPreferences(val memberId : Long, val accessToken: String, val refreshToken: String)
