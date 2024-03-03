package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.network.model.NetworkScrap
import store.newsbriefing.app.core.network.model.NetworkScrapCreate
import store.newsbriefing.app.core.network.model.NetworkScrapDelete


interface ScrapNetworkDataSource {
    suspend fun getScrap(memberId: Long): List<NetworkScrap>

    suspend fun setScrap(memberId: Long, articleId: Long): NetworkScrapCreate

    suspend fun unScrap(memberId: Long, articleId: Long): NetworkScrapDelete
}