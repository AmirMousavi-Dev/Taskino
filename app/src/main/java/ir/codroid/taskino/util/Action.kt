package ir.codroid.taskino.util

import androidx.annotation.StringRes
import ir.codroid.taskino.R

enum class Action(@StringRes val title : Int) {
    ADD(R.string.add),
    UPDATE(R.string.update),
    DELETE(R.string.delete),
    DELETE_ALL(R.string.delete_all),
    UNDO(R.string.undo_delete),
    NO_ACTION(0),
}
fun String?.toAction() = when{
    this == "ADD" -> {
        Action.ADD
    }
    this == "UPDATE" -> {
        Action.UPDATE
    }
    this == "DELETE" -> {
        Action.DELETE
    }
    this == "DELETE_ALL" -> {
        Action.DELETE_ALL
    }
    this == "UNDO" -> {
        Action.UNDO
    }
    else -> {
        Action.NO_ACTION
    }
}