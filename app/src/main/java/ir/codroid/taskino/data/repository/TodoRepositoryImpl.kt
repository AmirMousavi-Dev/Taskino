package ir.codroid.taskino.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.taskino.data.local.ToDoDao
import ir.codroid.taskino.domain.model.ToDoTask
import ir.codroid.taskino.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepositoryImpl @Inject constructor(private val todoDao: ToDoDao) : TodoRepository {

    override fun getSelectedTask(taskId: Int) = todoDao.getSelectedTask(taskId)
    override fun getAllTask(): Flow<List<ToDoTask>> = todoDao.getAllTasks()
    override fun getAllTaskSortByLowPriority(): Flow<List<ToDoTask>> = todoDao.sortByLowPriority()

    override fun getAllTaskSortByHighPriority(): Flow<List<ToDoTask>> = todoDao.sortByHighPriority()

    override fun searchTask(searchQuery: String) = todoDao.searchTasks(searchQuery)

    override suspend fun addTask(toDoTask: ToDoTask) {
        todoDao.addTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        todoDao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTask() {
        todoDao.deleteAllTasks()
    }
}