package store.newsbriefing.app.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import store.newsbriefing.app.core.network.datasource.BriefingNetworkDataSource
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.datasource.ScrapNetworkDataSource
import store.newsbriefing.app.core.network.retrofit.api.RetrofitBriefingNetwork
import store.newsbriefing.app.core.network.retrofit.api.RetrofitMemberNetwork
import store.newsbriefing.app.core.network.retrofit.api.RetrofitScrapNetwork

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {
    @Binds
    fun bindBriefingNetworkDataSource(
        retrofitBriefingNetwork: RetrofitBriefingNetwork
    ): BriefingNetworkDataSource

    @Binds
    fun bindMemberNetworkDataSource(
        retrofitMemberNetwork: RetrofitMemberNetwork
    ): MemberNetworkDataSource

    @Binds
    fun bindScrapNetworkDataSource(
        retrofitScrapNetwork: RetrofitScrapNetwork
    ): ScrapNetworkDataSource
}
