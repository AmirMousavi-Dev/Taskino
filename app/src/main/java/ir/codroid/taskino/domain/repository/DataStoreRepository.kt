package ir.codroid.taskino.domain.repository

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.util.Language
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveSortState(priority: Priority)
    fun readSortState(): Flow<String>
    suspend fun saveUserLanguage(language: Language)
    fun readUserLanguage(): Flow<String>
}