package com.gsm.presentation.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gsm.presentation.data.DataStoreRepository.PreferencesKeys.dataStoreName
import com.gsm.presentation.data.DataStoreRepository.PreferencesKeys.dataStoreToken
import com.gsm.presentation.util.Constant.Companion.DEFAULT_NAME
import com.gsm.presentation.util.Constant.Companion.DEFAULT_TOKEN
import com.gsm.presentation.util.Constant.Companion.PREFERENCES_TOKEN
import com.gsm.presentation.util.Constant.Companion.PREFERENCES_USER_NAME
import com.gsm.presentation.util.Constant.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {


    private object PreferencesKeys {
        val dataStoreToken = stringPreferencesKey(PREFERENCES_TOKEN)
        val dataStoreName = stringPreferencesKey(PREFERENCES_USER_NAME)


    }

    private val dataStore: DataStore<Preferences> =
        context.dataStore


    // 데이터를 쓴다
    suspend fun saveToken(token: String) {

        dataStore.edit { preferences ->
            preferences[dataStoreToken] = token
            Log.d("DataStoreRepository", "saveToken: ${preferences[dataStoreToken]}")

        }
    }

    suspend fun saveName(name: String) {

        dataStore.edit { preferences ->
            preferences[dataStoreName] = name
            Log.d("DataStoreRepository", "saveName: ${preferences[dataStoreName]}")

        }
    }

    val readToken: Flow<Token> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val token = preferences[dataStoreToken] ?: DEFAULT_TOKEN
            Log.d("DataStoreRepository", "readToken  $token")
            Token(token)
        }

    val readName: Flow<Name> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val name = preferences[dataStoreName] ?: DEFAULT_NAME
            Log.d("DataStoreRepository", "readName  $name")
            Name(name)
        }


}

data class Token(
    val token: String,
)
data class Name(
    val name: String,
)