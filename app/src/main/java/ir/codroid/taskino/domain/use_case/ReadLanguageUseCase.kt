package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import ir.codroid.taskino.util.Language
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ReadLanguageUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = runBlocking {
        repository.readUserLanguage().map {
            Language.valueOf(
                it
            )
        }
    }
}