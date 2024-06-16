package store.newsbriefing.app.core.network.datasource

import store.newsbriefing.app.core.network.model.NetworkScrap


interface ScrapNetworkDataSource {
    suspend fun getScrap(memberId: Long): List<NetworkScrap>

    suspend fun setScrap(memberId: Long, articleId: Long)

    suspend fun unScrap(memberId: Long, articleId: Long)
}