package ir.codroid.taskino.util

object Constants {
    const val TODO_TASK_TABLE = "todo_task_table"
    const val TASKINO_DATABASE = "taskino_database"



    const val LIST_ARGUMENT_KEY = "action"
    const val TASK_ARGUMENT_KEY = "taskId"
    const val LIST_SCREEN = "list/{$LIST_ARGUMENT_KEY}"
    const val TASK_SCREEN = "task/{$TASK_ARGUMENT_KEY}"
    const val SPLASH_SCREEN = "splash"
    const val SPLASH_SCREEN_DELAY = 3000L

    const val APPLICATION_PACKAGE_NAME = "ir.codroid.taskino"
    const val CAFE_BAZAR_PACKAGE_NAME = "com.farsitel.bazaar"
    const val CAFE_BAZAR_DEVELOPER_ID = "reversedev"


    const val PREFERENCE_NAME ="todo_preferences"
    const val PREFERENCE_SORT_KEY ="sort_state_key"
    const val PREFERENCE_LANGUAGE_KEY = "language_key"

    var USER_LANGUAGE = "USER_LANGUAGE"
}