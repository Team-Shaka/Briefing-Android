package store.newsbriefing.app.core.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import store.newsbriefing.app.core.data.repository.BriefingRepository
import store.newsbriefing.app.core.data.repository.DefaultBriefingRepository
import store.newsbriefing.app.core.data.repository.DefaultMemberRepository
import store.newsbriefing.app.core.data.repository.DefaultScrapRepository
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.data.repository.ScrapRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindBriefingRepository(
        repository: DefaultBriefingRepository
    ): BriefingRepository

    @Binds
    internal abstract fun bindMemberRepository(
        repository: DefaultMemberRepository
    ): MemberRepository

    @Binds
    internal abstract fun bindScrapRepository(
        repository: DefaultScrapRepository
    ): ScrapRepository
}