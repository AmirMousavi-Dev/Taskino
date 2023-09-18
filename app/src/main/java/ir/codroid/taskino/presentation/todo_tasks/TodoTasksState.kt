package ir.codroid.taskino.presentation.todo_tasks

import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.util.Language
import ir.codroid.taskino.util.SearchAppbarState

data class TodoTasksState(
    val todoTasks: List<ToDoTask> = emptyList(),
    val priority: Priority = Priority.NONE,
    val priorityDropDown: Boolean = false,
    val moreDropDown: Boolean = false,
    val deleteAllDialog: Boolean = false,
    val languageDialog: Boolean = false,
    val language: Language = Language.ENGLISH,
    val searchAppBaseState: SearchAppbarState = SearchAppbarState.CLOSED,
    val searchQuery: String = ""
)
