package ir.codroid.taskino.util

import androidx.annotation.StringRes
import ir.codroid.taskino.R

enum class Language(@StringRes val title: Int, val languageCode: String) {
    ENGLISH(title = R.string.english, languageCode = "en"),
    PERSIAN(title = R.string.persian, languageCode = "fa")
}