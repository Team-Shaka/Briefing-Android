package store.newsbriefing.app.feature.auth.di

import android.content.Context
import androidx.credentials.CredentialManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/*
@Module
@InstallIn(SingletonComponent::class)
abstract class CredentialManagerModule {
    @Provides
    internal fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }
}*/
