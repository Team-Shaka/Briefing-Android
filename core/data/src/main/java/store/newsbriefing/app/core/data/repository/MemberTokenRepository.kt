package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.MemberToken

interface MemberTokenRepository {
    fun getMemberToken() : Flow<MemberToken>
    suspend fun saveMemberToken(memberId : Long, accessToken: String, refreshToken: String)
}