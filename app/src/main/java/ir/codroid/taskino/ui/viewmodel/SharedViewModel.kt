package ir.codroid.taskino.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

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