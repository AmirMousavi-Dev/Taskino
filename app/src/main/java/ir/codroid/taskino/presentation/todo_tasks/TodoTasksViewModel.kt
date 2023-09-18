package ir.codroid.taskino.presentation.todo_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.domain.model.Priority
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.use_case.ListUseCases
import ir.codroid.taskino.util.SearchAppbarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoTasksViewModel @Inject constructor(
    private val listUseCases: ListUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<TodoTasksState>(TodoTasksState())
    val state: StateFlow<TodoTasksState> = _state.asStateFlow()

    var recentlyDeletedTask: ToDoTask? = null

    init {
        getAllTodoTasks()
    }

    fun onEvent(event: TodoTasksEvent) {
        when (event) {
            is TodoTasksEvent.Order -> {
                getAllTodoTasks(event.priority)
                _state.value = state.value.copy(
                    priorityDropDown = false
                )
            }

            is TodoTasksEvent.DeleteTodoTask -> viewModelScope.launch {
                listUseCases.deleteTaskUseCase(event.todoTask)
                recentlyDeletedTask = event.todoTask
            }

            is TodoTasksEvent.DeleteAllTodoTasks -> viewModelScope.launch {
                listUseCases.deleteAllTaskUseCase()
            }

            is TodoTasksEvent.RestoreTodoTask -> viewModelScope.launch {
                recentlyDeletedTask?.let {
                    listUseCases.addTaskUseCase(it)
                    recentlyDeletedTask = null
                }
            }

            is TodoTasksEvent.SearchTodoTask -> getSearchedTodoTasks(event.searchQuery)
            is TodoTasksEvent.SelectLanguage -> viewModelScope.launch {
                listUseCases.saveLanguageUseCase(event.language)

            }

            is TodoTasksEvent.ToggleSearch -> {
                _state.value = state.value.copy(
                    searchAppBaseState = SearchAppbarState.OPENED
                )
            }

            is TodoTasksEvent.TogglePriorityDDM -> {
                _state.value = state.value.copy(
                    priorityDropDown = !state.value.priorityDropDown
                )
            }

            is TodoTasksEvent.ToggleMoreDDM -> {
                _state.value = state.value.copy(
                    moreDropDown = !state.value.moreDropDown
                )
            }

            is TodoTasksEvent.ToggleDeleteAllDialog -> {
                _state.value = state.value.copy(
                    deleteAllDialog = !state.value.deleteAllDialog
                )
            }

            is TodoTasksEvent.ToggleSelectLanguageDialog -> {
                _state.value = state.value.copy(
                    languageDialog = !state.value.languageDialog
                )
            }

            is TodoTasksEvent.ToggleCloseSearchAppbar -> {
                if (_state.value.searchQuery.isBlank()) {
                    getAllTodoTasks(state.value.priority)
                    _state.value = state.value.copy(
                        searchAppBaseState = SearchAppbarState.CLOSED
                    )
                } else
                    _state.value = state.value.copy(
                        searchQuery = ""
                    )
            }
        }
    }

    private fun getAllTodoTasks(priority: Priority = Priority.NONE) {
        listUseCases.getAllTaskUseCase(priority).onEach {
            _state.value = state.value.copy(
                todoTasks = it
            )
        }.launchIn(viewModelScope)
    }

    private fun getSearchedTodoTasks(searchQuery: String) {
        listUseCases.searchTaskUseCase(searchQuery).onEach {
            _state.value = state.value.copy(
                todoTasks = it
            )
        }
    }
}