package store.newsbriefing.app.core.model

data class MemberToken(
    val memberId: Long,
    val accessToken: String,
    val refreshToken: String
)