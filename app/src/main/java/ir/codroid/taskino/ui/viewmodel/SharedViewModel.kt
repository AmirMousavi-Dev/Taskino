package ir.codroid.taskino.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.data.repository.DataStoreRepository
import ir.codroid.taskino.data.repository.TodoRepository
import ir.codroid.taskino.ui.theme.MAX_TITLE_LENGTH
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.RequestState
import ir.codroid.taskino.util.SearchAppbarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val searchAppbarState: MutableState<SearchAppbarState> =
        mutableStateOf(SearchAppbarState.CLOSED)
    val searchAppbarTextState: MutableState<String> = mutableStateOf("")

    // region get Task from Database
    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.NotInitialize)
    val allTasks = _allTasks.asStateFlow()

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.NotInitialize)
    val searchedTasks = _searchedTasks.asStateFlow()


    private val _selectedTask =
        MutableStateFlow<RequestState<ToDoTask?>>(RequestState.NotInitialize)
    val selectedTask = _selectedTask.asStateFlow()

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.NotInitialize)
    val sortState = _sortState.asStateFlow()

    val lowPriorityTasks : StateFlow<List<ToDoTask>> = repository
        .sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTask>> = repository
        .sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    // endregion get Task from Database

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)


    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {

            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.emit(RequestState.Success(it))
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun getSearchedTasks(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchTask("%$searchQuery%").collect { searchedTasks ->
                    _searchedTasks.value = RequestState.Success(searchedTasks)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppbarState.value = SearchAppbarState.TRIGGERED
    }

    fun getSelectedTask(taskId: Int) {
        try {

            viewModelScope.launch {
                repository.getSelectedTask(taskId = taskId).collect { task ->
                    _selectedTask.emit(RequestState.Success(task))
                }
            }
        } catch (e: Exception) {
            _selectedTask.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    fun readSortSate() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.readSortState
                .map { priorityName ->
                    Priority.valueOf(priorityName)
                }
                .collect { priority ->
                    _sortState.emit(RequestState.Success(priority))
                }
        }
    }

    // region update filed
    fun updateTaskFiled(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH)
            title.value = newTitle
    }

    fun validateFields() =
        title.value.isNotEmpty() && description.value.isNotEmpty()

    // endregion update filed

    // region database action
    private fun addTask() {
        val toDoTask = ToDoTask(
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppbarState.value = SearchAppbarState.CLOSED
    }

    private fun updateTask() {
        val toDoTask = ToDoTask(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        val toDoTask = ToDoTask(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTasks()
            }

            Action.UNDO -> {
                addTask()
            }

            else -> {}
        }
    }

    // endregion database action

}