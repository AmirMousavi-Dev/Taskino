package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val repository: TodoRepository
) {

    operator fun invoke(): Flow<List<ToDoTask>> = repository.getAllTask()

}