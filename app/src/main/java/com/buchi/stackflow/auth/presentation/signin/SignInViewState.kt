package com.buchi.stackflow.auth.presentation.signin

import com.buchi.stackflow.auth.model.AuthEntity

data class SignInViewState(
    val signInResponse: AuthEntity.AuthResponse<String>? = null
)
