package com.buchi.core.utils

/**
 * All possible condition or state of processes from the view through the viewModel to the data source
 * @param data wrapped with an event class to allow handling of the returned data once. Only returned
 * when state is Success
 * @param error only returned when state is Error state, containing the cause of the error.
 * @param loading only true when a process is still ongoing, no success or error yet
 */
data class ResultState<T>(
    var data: Event<T>? = null,
    val error: Throwable? = null,
    val loading: Boolean? = false,
) {
    companion object {
        fun <T> loading() = ResultState<T>(
            data = null,
            error = null,
            loading = true,
        )

        fun <T> error(error: Throwable?) = ResultState<T>(
            data = null,
            error = error,
            loading = false,
        )

        fun <T> data(data: T?) = ResultState<T>(
            data = Event.dataEvent(data),
            error = null,
            loading = false,
        )
    }
}
