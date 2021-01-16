package com.buchi.stackflow.auth.data

import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import com.buchi.stackflow.utils.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AuthRepositoryImpl constructor(
    private val apiService: AuthService = RetrofitBuilder.retrofit().create(AuthService::class.java)
) : AuthRepository {

    override fun signIn(sigInBody: AuthEntity.SignInBody): Flow<ResultState<SignInViewState>> =
        flow<ResultState<SignInViewState>> {
            apiService.signIn(sigInBody)
        }.onStart {
            emit(ResultState.loading())
        }.catch {
            it.printStackTrace()
            emit(ResultState.error(it))
        }
            .flowOn(Dispatchers.IO)
}
