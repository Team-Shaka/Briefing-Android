package store.newsbriefing.app.core.data.repository.model

import store.newsbriefing.app.core.model.MemberToken
import store.newsbriefing.app.core.network.model.NetworkMemberToken

fun NetworkMemberToken.asExternalModel(): MemberToken {
    return MemberToken(
        memberId = memberId,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}