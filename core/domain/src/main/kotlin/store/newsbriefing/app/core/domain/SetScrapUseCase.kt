package store.newsbriefing.app.core.domain

import kotlinx.coroutines.flow.first
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.data.repository.ScrapRepository
import javax.inject.Inject

class SetScrapUseCase @Inject constructor(
    private val scrapRepository: ScrapRepository,
    private val memberTokenRepository: MemberTokenRepository
) {
    suspend operator fun invoke(articleId: Long) {
        val userId = memberTokenRepository.getMemberToken().first().memberId
        scrapRepository.setScrap(userId, articleId)
    }
}