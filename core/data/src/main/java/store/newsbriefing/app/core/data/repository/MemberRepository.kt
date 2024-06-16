package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.MemberToken
import store.newsbriefing.app.core.model.SocialProvider

interface MemberRepository {

    suspend fun deleteMember(memberId: Long)
    suspend fun getTokenWithSocialProvider(provider: SocialProvider, identityToken: String): Flow<MemberToken>
    suspend fun getRefreshedAccessToken(refreshToken: String): Flow<MemberToken>
}