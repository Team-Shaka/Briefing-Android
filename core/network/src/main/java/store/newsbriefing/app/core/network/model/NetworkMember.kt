package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName
import store.newsbriefing.app.core.common.toZoneDateTime
import store.newsbriefing.app.core.model.MemberDeleteResult
import store.newsbriefing.app.core.model.MemberToken

data class NetworkMemberDelete(
    @SerializedName("quitAt")
    val quitAt : String)

fun NetworkMemberDelete.asExternalModel() : MemberDeleteResult {
    return MemberDeleteResult(
        quitAt = quitAt.toZoneDateTime()
    )
}

data class NetworkMemberToken(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)

fun NetworkMemberToken.asExternalModel() : MemberToken {
    return MemberToken(
        memberId = memberId,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}