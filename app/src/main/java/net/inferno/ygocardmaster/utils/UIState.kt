package net.inferno.ygocardmaster.utils

data class UIState<T>(
    var data: T? = null,
    var error: Exception? = null,
    var loading: Boolean = false,
) {
    val isError: Boolean = error != null
    val hasData: Boolean = data != null
}