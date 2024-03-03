package store.newsbriefing.app.core.network.model.member

import com.google.gson.annotations.SerializedName

data class NetworkMemberToken(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) {
}