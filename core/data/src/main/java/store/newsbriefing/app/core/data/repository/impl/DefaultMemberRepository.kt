package store.newsbriefing.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.model.MemberToken
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.model.asExternalModel

internal class DefaultMemberRepository(private val memberNetworkDataSource: MemberNetworkDataSource) :
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