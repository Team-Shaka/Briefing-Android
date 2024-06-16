package store.newsbriefing.app.core.domain

import kotlinx.coroutines.flow.first
import store.newsbriefing.app.core.common.util.BriefingLogger
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.model.SocialProvider
import javax.inject.Inject

class SignInWithSocialProviderUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val memberTokenRepository: MemberTokenRepository
) {
    suspend operator fun invoke(provider : SocialProvider, identityToken : String) {
        val memberToken = memberRepository.getTokenWithSocialProvider(provider, identityToken).first()
        memberTokenRepository.saveMemberToken(memberToken.memberId, memberToken.accessToken, memberToken.refreshToken)
        BriefingLogger.i("signed in member Id : ${memberToken.memberId}")
    }
}