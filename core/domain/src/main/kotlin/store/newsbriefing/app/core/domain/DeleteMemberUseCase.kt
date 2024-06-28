package store.newsbriefing.app.core.domain

import kotlinx.coroutines.flow.first
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val memberTokenRepository: MemberTokenRepository
) {
    suspend operator fun invoke() {
        val memberId = memberTokenRepository.getMemberToken().first().memberId
        memberRepository.deleteMember(memberId)
        memberTokenRepository.clearMemberToken()
    }
}