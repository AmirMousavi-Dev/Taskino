package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TodoRepository
) {

    suspend operator fun invoke(toDoTask: ToDoTask) = repository.deleteTask(toDoTask)
}