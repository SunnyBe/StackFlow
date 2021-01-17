package com.buchi.stackflow.auth.data

import android.util.Log
import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import com.buchi.stackflow.auth.presentation.signup.SignUpViewState
import com.buchi.stackflow.utils.OkHttpHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AuthRepositoryImpl constructor(
    private val okhttpService: OkHttpHelper = OkHttpHelper("https://stackoverflw.herokuapp.com/v1/")
) : AuthRepository {

    override fun signIn(sigInBody: AuthEntity.SignInBody): Flow<ResultState<SignInViewState>> =
        flow<ResultState<SignInViewState>> {
            val response = okhttpService.makeRequest("user/signin", sigInBody.makeFormBody())
            Log.d(javaClass.simpleName, "Response: $response")
            if (response.isSuccessful) {
                val apiResp = Gson().fromJson<AuthEntity.AuthResponse<String>>(
                    response.body?.string(),
                    AuthEntity.AuthResponse::class.java
                )
                emit(ResultState.data(SignInViewState(signInResponse = apiResp)))
            } else {
                emit(ResultState.error(Throwable("${response.code} ${response.message}")))
            }
        }.onStart {
            emit(ResultState.loading())
        }.catch {
            it.printStackTrace()
            emit(ResultState.error(it))
        }
            .flowOn(Dispatchers.IO)

    override fun signUp(signUpBody: AuthEntity.SignUpBody): Flow<ResultState<SignUpViewState>> {
        return flow<ResultState<SignUpViewState>> {
            val response = okhttpService.makeRequest("user/signup", signUpBody.makeFormBody())
            Log.d(javaClass.simpleName, "Response: $response")
            if (response.isSuccessful) {
                val apiResp = Gson().fromJson<AuthEntity.AuthResponse<String>>(
                    response.body?.string(),
                    AuthEntity.AuthResponse::class.java
                )
                emit(ResultState.data(SignUpViewState(signUpResponse = apiResp)))
            } else {
                emit(ResultState.error(Throwable("${response.code} ${response.message}")))
            }
        }.onStart {
            emit(ResultState.loading())
        }.catch {
            it.printStackTrace()
            emit(ResultState.error(it))
        }
            .flowOn(Dispatchers.IO)
    }
}
