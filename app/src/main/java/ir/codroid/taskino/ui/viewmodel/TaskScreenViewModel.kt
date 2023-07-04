package ir.codroid.taskino.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.taskino.data.model.Priority
import ir.codroid.taskino.data.model.ToDoTask
import ir.codroid.taskino.repository.TodoRepository
import ir.codroid.taskino.ui.theme.MAX_TITLE_LENGTH
import ir.codroid.taskino.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    private val _selectedTask =
        MutableStateFlow<RequestState<ToDoTask?>>(RequestState.NotInitialize)
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

    fun updateTitle (newTitle :String) {
        if (newTitle.length < MAX_TITLE_LENGTH)
            title.value = newTitle
    }

    fun validateFields () =
        title.value.isNotEmpty() && description.value.isNotEmpty()
}