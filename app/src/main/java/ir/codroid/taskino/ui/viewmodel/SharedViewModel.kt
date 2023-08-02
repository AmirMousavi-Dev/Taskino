package ir.codroid.taskino.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.TaskColor
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.data.repository.DataStoreRepository
import ir.codroid.taskino.data.repository.TodoRepository
import ir.codroid.taskino.ui.theme.MAX_TITLE_LENGTH
import ir.codroid.taskino.util.Action
import ir.codroid.taskino.util.Language
import ir.codroid.taskino.util.RequestState
import ir.codroid.taskino.util.SearchAppbarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var searchAppbarState by mutableStateOf(SearchAppbarState.CLOSED)
        private set

    var searchAppbarTextState by mutableStateOf("")
        private set

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


    val lowPriorityTasks: StateFlow<List<ToDoTask>> = repository
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

    var id by mutableIntStateOf(0)
        private set

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set
    var taskColor by mutableStateOf(TaskColor.DEFAULT)
        private set
    var action by mutableStateOf(Action.NO_ACTION)
        private set
    var userLanguage by mutableStateOf(Language.ENGLISH)
        private set

    init {
        getAllTasks()
        readSortSate()

    }

    private fun getAllTasks() {
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
        searchAppbarState = SearchAppbarState.TRIGGERED
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

    private fun readSortSate() {
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

    fun saveUserLanguage(language: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveUserLanguage(language = language)
        }
    }

     fun readUserLanguage() :Language =
        dataStoreRepository.readUserLanguage()


    // region update filed
    fun updateTaskFiled(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
            taskColor = selectedTask.color
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
            taskColor = TaskColor.DEFAULT
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH)
            title = newTitle
    }

    fun validateFields() =
        title.isNotEmpty() && description.isNotEmpty()

    // endregion update filed

    // region database action
    private fun addTask() {
        val toDoTask = ToDoTask(
            title = title,
            description = description,
            priority = priority,
            color = taskColor
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppbarState = SearchAppbarState.CLOSED
    }

    private fun updateTask() {
        val toDoTask = ToDoTask(
            id = id,
            title = title,
            description = description,
            priority = priority,
            color = taskColor

        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        val toDoTask = ToDoTask(
            id = id,
            title = title,
            description = description,
            priority = priority,
            color = taskColor
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

    // region setter
    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateColor(newTaskColor: TaskColor) {
        taskColor = newTaskColor
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateSearchAppbarState(newAppbarState: SearchAppbarState) {
        searchAppbarState = newAppbarState
    }

    fun updateSearchAppbarTextState(newSearchAppbarState: String) {
        searchAppbarTextState = newSearchAppbarState
    }

    fun updateUserLanguage(newLanguage: Language) {
        userLanguage = newLanguage
    }
    // endregion setter

}