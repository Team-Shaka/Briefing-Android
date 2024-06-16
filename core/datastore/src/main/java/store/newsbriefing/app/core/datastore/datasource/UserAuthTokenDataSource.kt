package store.newsbriefing.app.core.datastore.datasource

import kotlinx.coroutines.flow.Flow
import store.newsbriefing.app.core.datastore.model.UserAuthTokenPreferences

interface UserAuthTokenDataSource {
    fun getUserAuthToken(): Flow<UserAuthTokenPreferences>
    suspend fun clear()
    suspend fun saveUserAuthToken(memberId : Long, accessToken: String, refreshToken: String)
}