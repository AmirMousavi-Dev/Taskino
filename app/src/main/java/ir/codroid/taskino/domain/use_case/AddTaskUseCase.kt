package ir.codroid.taskino.domain.use_case

import ir.codroid.taskino.domain.model.InvalidTodoTaskException
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import ir.codroid.taskino.util.Language
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TodoRepository
) {

    @Throws(InvalidTodoTaskException::class)
    suspend operator fun invoke(toDoTask: ToDoTask, language: Language) {
        if (toDoTask.title.isBlank()) {
            throw InvalidTodoTaskException(
                if (language == Language.PERSIAN)
                    "The title of the todoTask can't be empty!"
                else
                    "عنوان نمیتواند خالی باشد!"
            )
        }
        if (toDoTask.description.isBlank()) {
            throw InvalidTodoTaskException(
                if (language == Language.PERSIAN)
                    "The description of the todoTask can't be empty!"
                else
                    "توضیحات نمیتواند خالی باشد!"
            )
        }
        repository.addTask(toDoTask)
    }
}