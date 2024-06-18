package store.newsbriefing.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.data.repository.ScrapRepository
import store.newsbriefing.app.core.model.BriefingScrapArticle
import javax.inject.Inject

class LoadBriefingScrapArticleUseCase @Inject constructor(
    private val scrapRepository: ScrapRepository,
    private val memberTokenRepository: MemberTokenRepository
) {
    suspend operator fun invoke(): Flow<List<BriefingScrapArticle>> {
        val userId = memberTokenRepository.getMemberToken().first().memberId
        val scraps = scrapRepository.getScrap(userId).first()

        val groupedScraps = scraps.groupBy { it.date }

        return flow { emit(groupedScraps.map { BriefingScrapArticle(it.key, it.value) }) }
    }
}