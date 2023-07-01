package ir.codroid.taskino.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.codroid.taskino.util.Constants.TODO_TASK_TABLE

@Entity(tableName = TODO_TASK_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)