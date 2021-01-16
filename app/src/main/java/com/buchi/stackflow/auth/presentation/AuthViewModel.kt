package com.buchi.stackflow.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    fun testString(): String = "Test string"

    private val _showProgress = ConflatedBroadcastChannel(false)
    val showProgress = _showProgress.asFlow().asLiveData()

    private val _error = ConflatedBroadcastChannel<Throwable?>(null)
    val error = _error.asFlow().asLiveData()
}
