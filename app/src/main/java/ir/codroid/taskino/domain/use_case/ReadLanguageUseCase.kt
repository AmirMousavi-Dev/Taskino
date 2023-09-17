package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import javax.inject.Inject

class ReadLanguageUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {

}