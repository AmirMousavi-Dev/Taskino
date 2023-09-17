package ir.codroid.taskino.domain.repository

import ir.codroid.taskino.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun addTask(toDoTask: ToDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask)

    suspend fun deleteAllTask()

    fun getSelectedTask(taskId: Int): ToDoTask?

    fun getAllTask(): Flow<List<ToDoTask>>

    fun searchTask(searchQuery: String): Flow<List<ToDoTask>>


}