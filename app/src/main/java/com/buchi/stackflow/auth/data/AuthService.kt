package com.buchi.stackflow.auth.data

import com.buchi.data.datasource.network.ApiService
import com.buchi.stackflow.auth.model.AuthEntity
import retrofit2.http.Body
import retrofit2.http.GET

interface AuthService : ApiService {

    @GET
    suspend fun signIn(
        @Body loginBody: AuthEntity.SignInBody
    ): AuthEntity.AuthResponse<String>
}
