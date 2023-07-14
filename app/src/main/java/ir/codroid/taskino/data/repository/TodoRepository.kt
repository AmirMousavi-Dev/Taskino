package ir.codroid.taskino.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.taskino.data.ToDoDao
import ir.codroid.taskino.data.model.ToDoTask
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks = toDoDao.getAllTasks()
    val sortByLowPriority = toDoDao.sortByLowPriority()
    val sortByHighPriority = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)
    fun searchTask(searchQuery: String) = toDoDao.searchTasks(searchQuery)

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTask() {
        toDoDao.deleteAllTasks()
    }

}