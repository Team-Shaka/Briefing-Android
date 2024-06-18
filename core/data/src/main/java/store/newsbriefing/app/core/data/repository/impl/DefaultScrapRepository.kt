package store.newsbriefing.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.data.repository.ScrapRepository
import store.newsbriefing.app.core.data.repository.model.asExternalModel
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.network.datasource.ScrapNetworkDataSource
import javax.inject.Inject

internal class DefaultScrapRepository @Inject constructor(
    private val scrapNetworkDataSource: ScrapNetworkDataSource
) : ScrapRepository {
    override fun getScrap(memberId: Long): Flow<List<Scrap>> = flow {
        val scraps = scrapNetworkDataSource.getScrap(memberId).map { it.asExternalModel() }
        emit(scraps)
    }

    override suspend fun setScrap(memberId: Long, articleId: Long) {
        return scrapNetworkDataSource.setScrap(memberId, articleId)
    }

    override suspend fun unScrap(memberId: Long, articleId: Long) {
        return scrapNetworkDataSource.unScrap(memberId, articleId)
    }

}