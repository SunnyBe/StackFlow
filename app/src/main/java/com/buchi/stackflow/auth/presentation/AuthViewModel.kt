package com.buchi.stackflow.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

class AuthViewModel constructor() : ViewModel() {
    private val _showProgress = ConflatedBroadcastChannel(false)
    val showProgress = _showProgress.asFlow().asLiveData()

    private val _error = ConflatedBroadcastChannel<Throwable?>(null)
    val error = _error.asFlow().asLiveData()
}
