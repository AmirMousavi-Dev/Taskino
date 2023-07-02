package ir.codroid.taskino.util

sealed class RequestState<T> {
    object NotInitialize : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: Throwable) : RequestState<Throwable>()
}
