package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.network.model.scrap.NetworkScrap
import store.newsbriefing.app.core.network.model.scrap.NetworkScrapCreate
import store.newsbriefing.app.core.network.model.scrap.NetworkScrapDelete

interface ScrapNetworkDataSource {
    suspend fun getScrap(memberId: Long): List<NetworkScrap>

    suspend fun setScrap(memberId: Long, articleId: Long): NetworkScrapCreate

    suspend fun unScrap(memberId: Long, articleId: Long): NetworkScrapDelete
}