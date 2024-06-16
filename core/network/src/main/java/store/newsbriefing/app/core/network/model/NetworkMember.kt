package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName
import store.newsbriefing.app.core.model.MemberToken

internal data class NetworkMemberDeleteResponse(
    @SerializedName("quitAt")
    val quitAt: String
)

data class NetworkMemberToken(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)