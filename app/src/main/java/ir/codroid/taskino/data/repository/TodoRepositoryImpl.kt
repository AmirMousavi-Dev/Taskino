package ir.codroid.taskino.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.taskino.data.local.ToDoDao
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepositoryImpl @Inject constructor(private val toDoDao: ToDoDao) :TodoRepository {

    override fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)
    override fun getAllTask(): Flow<List<ToDoTask>> = toDoDao.getAllTasks()

    override fun searchTask(searchQuery: String) = toDoDao.searchTasks(searchQuery)

    override suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTask() {
        toDoDao.deleteAllTasks()
    }
}