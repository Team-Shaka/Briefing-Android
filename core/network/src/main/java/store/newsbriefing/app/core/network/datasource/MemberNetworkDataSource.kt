package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.network.model.NetworkMemberDeleteResponse
import store.newsbriefing.app.core.network.model.NetworkMemberToken


interface MemberNetworkDataSource {
    suspend fun deleteMember(memberId: Long)
    suspend fun getTokenWithSocialProvider(
        provider: String,
        identityToken: String
    ): NetworkMemberToken

    suspend fun getRefreshedAccessToken(
        refreshToken: String
    ): NetworkMemberToken
}