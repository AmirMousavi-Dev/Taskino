package ir.codroid.taskino.util

import androidx.annotation.StringRes
import ir.codroid.taskino.R
import ir.codroid.taskino.data.model.Priority

enum class Action(@StringRes val title: Int) {
    ADD(R.string.add),
    UPDATE(R.string.update),
    DELETE(R.string.delete),
    DELETE_ALL(R.string.delete_all),
    UNDO(R.string.undo_delete),
    NO_ACTION(0),
}

fun String?.toAction(): Action =
    if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)