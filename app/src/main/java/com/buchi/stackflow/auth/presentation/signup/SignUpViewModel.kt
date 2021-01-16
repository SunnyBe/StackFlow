package com.buchi.stackflow.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.data.AuthRepository
import com.buchi.stackflow.auth.presentation.signin.SignInStateEvent
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SignUpViewModel constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    private val stateChannel = ConflatedBroadcastChannel<SignInViewState>()
    private val eventChannel = ConflatedBroadcastChannel<SignInStateEvent>(SignInStateEvent.Idle())

    val viewState = stateChannel.asFlow().asLiveData()
    val dataState = eventChannel.asFlow()
        .flatMapLatest { stateEvent ->
            processActions(stateEvent)
        }
        .asLiveData()

    private fun processActions(stateEvent: SignInStateEvent): Flow<ResultState<SignInViewState>> {
        return when (stateEvent) {
            is SignInStateEvent.SignIn -> {
                authRepo.signIn(stateEvent.signInBody)
            }
            is SignInStateEvent.Idle -> {
                flow { emit(ResultState.data(SignInViewState())) }
            }
        }
    }
}
