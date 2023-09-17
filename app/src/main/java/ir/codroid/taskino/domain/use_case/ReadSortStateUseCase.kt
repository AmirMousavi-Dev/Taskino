package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadSortStateUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = flow<Priority>{
        repository.readSortState().map { Priority.valueOf(it) }
    }
}