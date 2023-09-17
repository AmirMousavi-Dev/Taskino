package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import ir.codroid.taskino.util.Language
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(language: Language) {
        runBlocking {
            repository.saveUserLanguage(language = language)
        }
    }
}