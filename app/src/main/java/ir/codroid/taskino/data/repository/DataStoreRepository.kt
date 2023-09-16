package ir.codroid.taskino.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.util.Constants.PREFERENCE_LANGUAGE_KEY
import ir.codroid.taskino.util.Constants.PREFERENCE_NAME
import ir.codroid.taskino.util.Constants.PREFERENCE_SORT_KEY
import ir.codroid.taskino.util.Language
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(PREFERENCE_SORT_KEY)
        val languageKey = stringPreferencesKey(PREFERENCE_LANGUAGE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            val sortData = preferences[PreferenceKeys.sortKey] ?: Priority.NONE.name
            sortData
        }

    suspend fun saveUserLanguage(language: Language) =
        runBlocking {
            Log.e("3169" , "d ${language.languageCode}")
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.languageKey] = language.name
        }
            delay(600)
    }

    fun readUserLanguage(): Language = runBlocking {
        Language.valueOf(
            dataStore.data.first()[PreferenceKeys.languageKey] ?: Language.ENGLISH.name
        )
    }


}