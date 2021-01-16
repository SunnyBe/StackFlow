package com.buchi.stackflow.auth.presentation.signin

import com.buchi.stackflow.auth.model.AuthEntity

sealed class SignInStateEvent {
    class SignIn(val signInBody: AuthEntity.SignInBody) : SignInStateEvent()
    class Idle() : SignInStateEvent()
}
