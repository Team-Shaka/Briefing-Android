package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.model.ScrapCreateResult
import store.newsbriefing.app.core.model.ScrapDeleteResult

interface ScrapRepository {
    fun getScrap(memberId: Long): Flow<List<Scrap>>
    fun setScrap(memberId: Long, articleId: Long): Flow<ScrapCreateResult>
    fun unScrap(memberId: Long, articleId: Long): Flow<ScrapDeleteResult>
}