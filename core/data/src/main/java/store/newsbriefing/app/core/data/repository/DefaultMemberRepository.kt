package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.model.MemberDeleteResult
import store.newsbriefing.app.core.model.MemberToken
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.model.asExternalModel

internal class DefaultMemberRepository(val memberNetworkDataSource: MemberNetworkDataSource) :
    MemberRepository {
    override suspend fun deleteMember(memberId: Long): Flow<MemberDeleteResult> = flow {
        emit(memberNetworkDataSource.deleteMember(memberId).asExternalModel())
    }

    override suspend fun getTokenWithSocialProvider(
        provider: String,
        identityToken: String
    ): Flow<MemberToken> = flow {
        emit(
            memberNetworkDataSource.getTokenWithSocialProvider(provider, identityToken)
                .asExternalModel()
        )
    }

    override suspend fun getRefreshedAccessToken(refreshToken: String): Flow<MemberToken> = flow {
        emit(memberNetworkDataSource.getRefreshedAccessToken(refreshToken).asExternalModel())
    }
}