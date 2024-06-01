package store.newsbriefing.app.core.datastore.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import store.newsbriefing.app.core.datastore.model.UserAuthTokenPreferences
import javax.inject.Inject

class DefaultUserAuthTokenDataSource @Inject constructor(val datastore: DataStore<Preferences>) :
    UserAuthTokenDataSource {

    private object PreferencesKeys {
        val MEMBER_ID  = longPreferencesKey("member_id")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    class MissingAuthTokenException : Exception("Missing auth token")

    override fun getUserAuthToken(): Flow<UserAuthTokenPreferences> =
        datastore.data.catch { exception ->
            if (exception is IOException) {
                Log.e("DataStore", "Error reading user auth token", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserAuthToken(preferences) ?: throw MissingAuthTokenException()
        }

    override suspend fun saveUserAuthToken(memberId : Long, accessToken: String, refreshToken: String) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.MEMBER_ID] = memberId
            preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
            preferences[PreferencesKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    private fun mapUserAuthToken(preferences: Preferences): UserAuthTokenPreferences? {
        val memberId = preferences[PreferencesKeys.MEMBER_ID]
        val accessToken = preferences[PreferencesKeys.ACCESS_TOKEN]
        val refreshToken = preferences[PreferencesKeys.REFRESH_TOKEN]
        return if (memberId != null && accessToken != null && refreshToken != null) {
            UserAuthTokenPreferences(memberId, accessToken, refreshToken)
        } else {
            null
        }
    }
}