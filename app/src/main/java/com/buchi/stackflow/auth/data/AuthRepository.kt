package com.buchi.stackflow.auth.data

import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import com.buchi.stackflow.auth.presentation.signup.SignUpViewState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /**
     * Make a request to sign in a user. The request is expected to be made to a network source
     * @param sigInBody , entity containing the items that make the body of the request.
     */
    fun signIn(sigInBody: AuthEntity.SignInBody): Flow<ResultState<SignInViewState>>

    /**
     * Make a request to sign up a new user. The request is expected to be made to a network source
     * @param signUpBody, entity containing the items that make the body of the request.
     */
    fun signUp(signUpBody: AuthEntity.SignUpBody): Flow<ResultState<SignUpViewState>>
}
