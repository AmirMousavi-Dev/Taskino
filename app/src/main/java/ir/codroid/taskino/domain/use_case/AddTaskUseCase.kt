package ir.codroid.taskino.domain.use_case

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.codroid.taskino.R
import ir.codroid.taskino.domain.model.InvalidTodoTaskException
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import ir.codroid.taskino.util.Language
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TodoRepository,
    @ApplicationContext private val context: Context
) {

    @Throws(InvalidTodoTaskException::class)
    suspend operator fun invoke(toDoTask: ToDoTask) {
        if (toDoTask.title.isBlank()) {
            throw InvalidTodoTaskException(
                context.getString(R.string.invalid_title)
            )
        }
        if (toDoTask.description.isBlank()) {
            throw InvalidTodoTaskException(
                context.getString(R.string.invalid_description)
            )
        }
        repository.addTask(toDoTask)
    }
}