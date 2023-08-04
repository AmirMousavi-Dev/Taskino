package ir.codroid.taskino.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.codroid.taskino.data.model.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao
}