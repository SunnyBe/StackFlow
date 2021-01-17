package com.buchi.stackflow.auth.model

import okhttp3.FormBody
import okhttp3.RequestBody

sealed class AuthEntity {

    data class AuthResponse<T : Any?>(
        val error: Boolean? = false,
        val code: Int?,
        val data: T?,
        val message: String?,
        val statusCode: String? = null
    )

    // Body for login request
    data class SignInBody(
        val email: String? = null,
        val password: String? = null
    ) : AuthEntity() {

        /**
         * This function is to create a request body from instance of the class SignInBody
         */
        fun makeFormBody(): RequestBody {
            return FormBody.Builder()
                .add("email", email ?: "")
                .add("password", password ?: "")
                .build()
        }

        companion object {
            /**
             * Static Utility function to create a request body from specified email and password
             * @param email to be passed into the form body
             * @param password to be passed into the form body
             */
            fun makeFormBody(email: String?, password: String?): RequestBody {
                return FormBody.Builder()
                    .add("email", email ?: "")
                    .add("password", password ?: "")
                    .build()
            }
        }
    }
}
