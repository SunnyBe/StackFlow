package com.buchi.question.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import okhttp3.FormBody
import okhttp3.RequestBody

sealed class QuestionEntity {

    data class QuestionResponse<T : Any?>(
        val error: Boolean? = false,
        val code: Int?,
        val data: T?,
        val message: String?,
        val statusCode: String? = null
    )

    @Parcelize
    data class QuestionListResponse(
        val error: Boolean? = false,
        val code: Int?,
        val data: List<Question>?,
        val message: String?,
        val statusCode: String? = null
    ) : Parcelable

    @Parcelize
    data class Question(
        val votes: Int? = null,
        val _id: String? = null,
        val question: String? = null,
        val user: String? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null
    ) : QuestionEntity(), Parcelable

    data class AnswerBody(
        val question: String?,
        val answer: String?
    ) {
        /**
         * This function is to create a request body from this instance of the class AnswerBody
         */
        fun makeFormBody(): RequestBody {
            return FormBody.Builder()
                .add("question", question ?: "")
                .add("answer", answer ?: "")
                .build()
        }

        companion object {
            /**
             * Static Utility function to create a request body from specified parameters
             * @param question to be passed into the form body
             * @param answer to be passed into the form body
             */
            fun makeFormBody(question: String?, answer: String?): RequestBody {
                return FormBody.Builder()
                    .add("question", question ?: "")
                    .add("answer", answer ?: "")
                    .build()
            }
        }
    }
}
/*
{
    "error": false,
    "code": 200,
    "data": [
        {
            "votes": 0,
            "_id": "6002a025ee33a70017ffa990",
            "question": "Am I going to feel bad about this?",
            "user": "60029fa5ee33a70017ffa98f",
            "createdAt": "2021-01-16T08:13:25.723Z",
            "updatedAt": "2021-01-16T08:13:25.723Z"
        },
        {
            "votes": 0,
            "_id": "6002a08dee33a70017ffa992",
            "question": "Am I going to feel bad about this?",
            "user": "60029fa5ee33a70017ffa98f",
            "createdAt": "2021-01-16T08:15:09.968Z",
            "updatedAt": "2021-01-16T08:15:09.968Z"
        },
        {
            "votes": 0,
            "_id": "6002a0b9ee33a70017ffa993",
            "question": "Why is kotlin is choosen over java?",
            "user": "60029fa5ee33a70017ffa98f",
            "createdAt": "2021-01-16T08:15:53.972Z",
            "updatedAt": "2021-01-16T08:15:53.972Z"
        }
    ],
    "message": "questions fetched successfully"
}
 */