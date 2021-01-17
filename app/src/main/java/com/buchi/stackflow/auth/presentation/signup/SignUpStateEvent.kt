package com.buchi.stackflow.auth.presentation.signup

import com.buchi.stackflow.auth.model.AuthEntity

sealed class SignUpStateEvent {
    class SignUp(val signInBody: AuthEntity.SignInBody) : SignUpStateEvent()
    class Idle() : SignUpStateEvent()
}
