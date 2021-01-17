package com.buchi.data.model

data class NetworkResponse<T : Any>(
    val error: Boolean = false,
    val code: Int?,
    val data: T?,
    val message: String?
)