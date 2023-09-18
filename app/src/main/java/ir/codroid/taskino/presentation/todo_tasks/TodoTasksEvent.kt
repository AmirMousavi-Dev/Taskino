package ir.codroid.taskino.presentation.todo_tasks

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.util.Language

sealed class TodoTasksEvent {
    data class Order(val priority: Priority) : TodoTasksEvent()
    data class DeleteTodoTask(val todoTask: ToDoTask) : TodoTasksEvent()
    object DeleteAllTodoTasks : TodoTasksEvent()
    object RestoreTodoTask : TodoTasksEvent()
    data class SearchTodoTask(val searchQuery: String) : TodoTasksEvent()
    data class SelectLanguage(val language: Language) : TodoTasksEvent()
    object ToggleSearch : TodoTasksEvent()
    object TogglePriorityDDM : TodoTasksEvent()
    object ToggleMoreDDM : TodoTasksEvent()
    object ToggleDeleteAllDialog : TodoTasksEvent()
    object ToggleSelectLanguageDialog : TodoTasksEvent()
    object ToggleCloseSearchAppbar : TodoTasksEvent()

}