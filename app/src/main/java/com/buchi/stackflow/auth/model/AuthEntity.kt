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

    // Body for SignUp request
    data class SignUpBody(
        val email: String? = null,
        val password: String? = null,
        val cPassword: String? = null,
        val firstName: String? = null,
        val lastName: String? = null
    ) : AuthEntity() {

        /**
         * This function is to create a request body from this instance of the class SignUpBody
         */
        fun makeFormBody(): RequestBody {
            return FormBody.Builder()
                .add("email", email ?: "")
                .add("password", password ?: "")
                .add("cPassword", cPassword ?: "")
                .add("firstname", firstName ?: "")
                .add("lastname", lastName ?: "")
                .build()
        }

        companion object {
            /**
             * Static Utility function to create a request body from specified parameters
             * @param email to be passed into the form body
             * @param password to be passed into the form body
             * @param firstName of the new user
             * @param lastName of the new user
             */
            fun makeFormBody(email: String?, password: String?, firstName: String?, lastName: String?): RequestBody {
                return FormBody.Builder()
                    .add("email", email ?: "")
                    .add("password", password ?: "")
                    .add("cPassword", password ?: "")
                    .add("firstname", firstName ?: "")
                    .add("lastname", lastName ?: "")
                    .build()
            }
        }
    }
}
