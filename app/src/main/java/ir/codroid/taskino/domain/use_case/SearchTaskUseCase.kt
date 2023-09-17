package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.repository.TodoRepository
import javax.inject.Inject

class SearchTaskUseCase @Inject constructor(
    private val repository: TodoRepository
) {
        operator fun invoke(searchQuery : String) = repository.searchTask(searchQuery)
}