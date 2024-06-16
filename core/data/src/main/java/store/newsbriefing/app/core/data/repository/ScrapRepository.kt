package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.model.Scrap

interface ScrapRepository {
    fun getScrap(memberId: Long): Flow<List<Scrap>>
    suspend fun setScrap(memberId: Long, articleId: Long)
    suspend fun unScrap(memberId: Long, articleId: Long)
}