package store.newsbriefing.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.datastore.datasource.UserAuthTokenDataSource
import store.newsbriefing.app.core.model.MemberToken

class DefaultMemberTokenRepository(private val userAuthTokenDataSource: UserAuthTokenDataSource) :
    MemberTokenRepository {
    override fun getMemberToken(): Flow<MemberToken> {
        return userAuthTokenDataSource.getUserAuthToken().map {
            MemberToken(it.memberId, it.accessToken, it.refreshToken)
        }
    }

    override suspend fun saveMemberToken(
        memberId: Long,
        accessToken: String,
        refreshToken: String
    ) {
        userAuthTokenDataSource.saveUserAuthToken(memberId, accessToken, refreshToken)
    }

}