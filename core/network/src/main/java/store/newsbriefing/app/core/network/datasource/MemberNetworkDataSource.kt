package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.network.model.member.NetworkMemberDelete
import store.newsbriefing.app.core.network.model.member.NetworkMemberToken

interface MemberNetworkDataSource {
    suspend fun deleteMember(memberId: Long): NetworkMemberDelete
    suspend fun getTokenWithSocialProvider(
        provider: String,
        identityToken: String
    ): NetworkMemberToken

    suspend fun getRefreshedAccessToken(
        refreshToken: String
    ): NetworkMemberToken
}