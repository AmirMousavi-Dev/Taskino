package ir.codroid.taskino.data.repository

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ir.codroid.taskino.data.model.TaskColor
import ir.codroid.taskino.util.Constants.TODO_TASK_TABLE

object DataBaseMigration {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE '$TODO_TASK_TABLE' " +
                        "ADD COLUMN 'color' TEXT " +
                        "NOT NULL " +
                        "DEFAULT ${TaskColor.DEFAULT_COLOR}"
            )

        }
    }
}