package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import javax.inject.Inject

class SaveSortStateUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(priority: Priority) {
        repository.saveSortState(priority = priority)
    }
}