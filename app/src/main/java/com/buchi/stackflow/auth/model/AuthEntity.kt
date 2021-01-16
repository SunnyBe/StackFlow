package com.buchi.stackflow.auth.model

sealed class AuthEntity {

    data class AuthResponse<T : Any?>(
        val error: Boolean = false,
        val code: Int?,
        val data: T?,
        val message: String?
    )

    // Body for login request
    data class SignInBody(
        val userName: String? = null,
        val password: String? = null
    ) : AuthEntity()
}
