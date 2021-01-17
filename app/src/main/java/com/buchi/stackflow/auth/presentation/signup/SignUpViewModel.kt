package com.buchi.stackflow.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.data.AuthRepository
import com.buchi.stackflow.auth.model.AuthEntity
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    private val stateChannel = ConflatedBroadcastChannel<SignUpViewState>()
    private val eventChannel = ConflatedBroadcastChannel<SignUpStateEvent>(SignUpStateEvent.Idle())

    val viewState = stateChannel.asFlow().asLiveData()
    val dataState = eventChannel.asFlow()
        .flatMapLatest { stateEvent ->
            processActions(stateEvent)
        }
        .asLiveData()

    private fun processActions(stateEvent: SignUpStateEvent): Flow<ResultState<SignUpViewState>> {
        return when (stateEvent) {
            is SignUpStateEvent.SignUp -> {
                authRepo.signUp(stateEvent.signUpBody)
            }
            is SignUpStateEvent.Idle -> {
                flow { emit(ResultState.data(SignUpViewState())) }
            }
        }
    }

    fun signUp(email: String, password: String, firstName: String?, lastName: String?) {
        val signUpBody = AuthEntity.SignUpBody(email, password, password, firstName, lastName)
        eventChannel.offer(SignUpStateEvent.SignUp(signUpBody = signUpBody))
    }

    fun setViewState(viewState: SignUpViewState) {
        stateChannel.offer(viewState)
    }
}
