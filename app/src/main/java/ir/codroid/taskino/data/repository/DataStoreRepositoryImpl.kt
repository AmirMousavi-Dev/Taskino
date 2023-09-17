package ir.codroid.taskino.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.taskino.data.data_store.TaskinoDataStore
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.util.Constants.PREFERENCE_LANGUAGE_KEY
import ir.codroid.taskino.util.Constants.PREFERENCE_NAME
import ir.codroid.taskino.util.Constants.PREFERENCE_SORT_KEY
import ir.codroid.taskino.util.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: TaskinoDataStore
) : DataStoreRepository {


    override suspend fun saveSortState(priority: Priority) {
        dataStore.putString(PREFERENCE_SORT_KEY, priority.name)
    }

    override fun readSortState(): Flow<String> = flow {
        emit(dataStore.getString(PREFERENCE_SORT_KEY) ?: Priority.NONE.name)
    }



    override suspend fun saveUserLanguage(language: Language) =
        dataStore.putString(PREFERENCE_LANGUAGE_KEY, language.name)


    override fun readUserLanguage(): Flow<String> = flow {
        emit(dataStore.getString(PREFERENCE_LANGUAGE_KEY) ?: Language.ENGLISH.name)
    }


}