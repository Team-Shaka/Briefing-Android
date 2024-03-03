package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.MemberDeleteResult
import store.newsbriefing.app.core.model.MemberToken

interface MemberRepository {

    suspend fun deleteMember(memberId: Long): Flow<MemberDeleteResult>
    suspend fun getTokenWithSocialProvider(provider: String, identityToken: String): Flow<MemberToken>
    suspend fun getRefreshedAccessToken(refreshToken: String): Flow<MemberToken>
}