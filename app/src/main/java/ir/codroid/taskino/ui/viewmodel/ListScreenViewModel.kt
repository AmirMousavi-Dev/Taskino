package ir.codroid.taskino.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.repository.TodoRepository
import ir.codroid.taskino.util.SearchAppbarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    val searchAppbarState : MutableState<SearchAppbarState> = mutableStateOf(SearchAppbarState.CLOSED)
    val searchAppbarTextState : MutableState<String> = mutableStateOf("")

    private val _taskList = MutableStateFlow<List<ToDoTask>>(emptyList())
    val taskList = _taskList.asStateFlow()

    fun getAllTask () {
        viewModelScope.launch {
            repository.getAllTasks.collect{
                _taskList.emit(it)
            }
        }
    }
}