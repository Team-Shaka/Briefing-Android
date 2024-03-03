package store.newsbriefing.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.model.ScrapCreateResult
import store.newsbriefing.app.core.model.ScrapDeleteResult
import store.newsbriefing.app.core.network.datasource.ScrapNetworkDataSource
import store.newsbriefing.app.core.network.model.asExternalModel

internal class DefaultScrapRepository(private val scrapNetworkDataSource: ScrapNetworkDataSource) : ScrapRepository {
    override fun getScrap(memberId: Long): Flow<List<Scrap>> = flow {
        val scraps = scrapNetworkDataSource.getScrap(memberId).map { it.asExternalModel() }
        emit(scraps)
    }

    override fun setScrap(memberId: Long, articleId: Long): Flow<ScrapCreateResult> = flow {
        val result = scrapNetworkDataSource.setScrap(memberId, articleId).asExternalModel()
        emit(result)
    }

    override fun unScrap(memberId: Long, articleId: Long): Flow<ScrapDeleteResult> = flow {
        val result = scrapNetworkDataSource.unScrap(memberId, articleId).asExternalModel()
        emit(result)
    }

}