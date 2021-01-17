package com.buchi.stackflow.auth.presentation.signup

import com.buchi.stackflow.auth.model.AuthEntity

data class SignUpViewState(
    val signUpResponse: AuthEntity.AuthResponse<String>? = null
)
