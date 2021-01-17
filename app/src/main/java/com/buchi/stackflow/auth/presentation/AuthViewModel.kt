package com.buchi.stackflow.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {

    private val _showProgress = ConflatedBroadcastChannel(false)
    val showProgress = _showProgress.asFlow().asLiveData()

    private val _error = ConflatedBroadcastChannel<Throwable?>(null)
    val error = _error.asFlow().asLiveData()

    @ExperimentalCoroutinesApi
    fun processError(throwable: Throwable? = null) {
        _error.offer(throwable)
    }

    fun toShowProgress(showProgress: Boolean) {
        _showProgress.offer(showProgress)
    }
}
