package store.newsbriefing.app.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import store.newsbriefing.app.core.network.datasource.BriefingNetworkDataSource
import store.newsbriefing.app.core.network.datasource.MemberNetworkDataSource
import store.newsbriefing.app.core.network.datasource.ScrapNetworkDataSource
import store.newsbriefing.app.core.network.retrofit.api.RetrofitBriefingNetworkDataSource
import store.newsbriefing.app.core.network.retrofit.api.RetrofitMemberNetworkDataSource
import store.newsbriefing.app.core.network.retrofit.api.RetrofitScrapNetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {
    @Binds
    fun bindBriefingNetworkDataSource(
        retrofitBriefingNetworkDataSource: RetrofitBriefingNetworkDataSource
    ): BriefingNetworkDataSource

    @Binds
    fun bindMemberNetworkDataSource(
        retrofitMemberNetworkDataSource: RetrofitMemberNetworkDataSource
    ): MemberNetworkDataSource

    @Binds
    fun bindScrapNetworkDataSource(
        retrofitScrapNetworkDataSource: RetrofitScrapNetworkDataSource
    ): ScrapNetworkDataSource
}
