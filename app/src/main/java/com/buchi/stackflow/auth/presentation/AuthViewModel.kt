package com.buchi.stackflow.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _showProgress = BroadcastChannel<Boolean>(Channel.CONFLATED)
    val showProgress = _showProgress.asFlow()

    private val _error = BroadcastChannel<Throwable?>(Channel.CONFLATED)
    val error = _error.asFlow()

    @ExperimentalCoroutinesApi
    fun processError(throwable: Throwable? = null) {
        viewModelScope.launch {
            _error.offer(throwable)
        }
    }

    fun toShowProgress(showProgress: Boolean) {
        viewModelScope.launch {
            _showProgress.offer(showProgress)
        }
    }
}
