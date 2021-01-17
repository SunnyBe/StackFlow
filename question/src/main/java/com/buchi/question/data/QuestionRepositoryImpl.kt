package com.buchi.question.data

import android.util.Log
import com.buchi.core.utils.OkHttpHelper
import com.buchi.core.utils.ResultState
import com.buchi.question.model.QuestionEntity
import com.buchi.question.presentation.dashboard.QuestionsViewState
import com.buchi.question.presentation.detail.QuestionDetailViewState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpHelper
) : QuestionRepository {

    override fun allQuestions(): Flow<ResultState<QuestionsViewState>> {
        return flow<ResultState<QuestionsViewState>> {
            val response = okHttpClient.makeRequest("question/")
            Log.d(javaClass.simpleName, "Response: $response")
            if (response.isSuccessful) {
                val apiResp =
                    Gson().fromJson(
                        response.body?.string(),
                        QuestionEntity.QuestionListResponse::class.java
                    )
                Log.d(javaClass.simpleName, "Response Body: $apiResp")
                emit(ResultState.data(QuestionsViewState(allQuestions = apiResp)))
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

    override fun voteQuestion(itemId: String?, vote: String): Flow<ResultState<QuestionDetailViewState>> {
        return flow<ResultState<QuestionDetailViewState>> {
            val response = okHttpClient.makeRequest("question/vote?questionId=$itemId&vote=$vote")
            Log.d(javaClass.simpleName, "Response: $response")
            if (response.isSuccessful) {
                val apiResp = Gson().fromJson<QuestionEntity.QuestionResponse<String>>(
                    response.body?.string(),
                    QuestionEntity.QuestionResponse::class.java
                )
                Log.d(javaClass.simpleName, "Response Body: $apiResp")
                emit(ResultState.data(QuestionDetailViewState(upVoted = apiResp)))
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

    override fun answerQuestion(body: QuestionEntity.AnswerBody): Flow<ResultState<QuestionDetailViewState>> {
        return flow<ResultState<QuestionDetailViewState>> {
            val response = okHttpClient.makeRequest("answer", body.makeFormBody())
            Log.d(javaClass.simpleName, "Response: $response")
            if (response.isSuccessful) {
                val apiResp = Gson().fromJson<QuestionEntity.QuestionResponse<String>>(
                    response.body?.string(),
                    QuestionEntity.QuestionResponse::class.java
                )
                Log.d(javaClass.simpleName, "Response Body: $apiResp")
                emit(ResultState.data(QuestionDetailViewState(answeredQuestion = apiResp)))
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