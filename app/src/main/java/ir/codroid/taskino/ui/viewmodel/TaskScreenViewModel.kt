package ir.codroid.taskino.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.repository.TodoRepository
import ir.codroid.taskino.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _selectedTask = MutableStateFlow<RequestState<ToDoTask?>>(RequestState.NotInitialize)
    val selectedTask = _selectedTask.asStateFlow()

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
}