package com.buchi.stackflow.auth.data

import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(loginBody: AuthEntity.SignInBody): Flow<ResultState<SignInViewState>>
}
