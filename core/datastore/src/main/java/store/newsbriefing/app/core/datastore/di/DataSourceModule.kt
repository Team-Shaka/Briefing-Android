package store.newsbriefing.app.core.datastore.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import store.newsbriefing.app.core.datastore.datasource.DefaultUserAuthTokenDataSource
import store.newsbriefing.app.core.datastore.datasource.UserAuthTokenDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindUserAuthTokenDataSource(defaultUserAuthTokenDataSource: DefaultUserAuthTokenDataSource) : UserAuthTokenDataSource
}