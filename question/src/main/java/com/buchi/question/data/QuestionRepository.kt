package com.buchi.question.data

import com.buchi.core.utils.ResultState
import com.buchi.question.model.QuestionEntity
import com.buchi.question.presentation.dashboard.QuestionsViewState
import com.buchi.question.presentation.detail.QuestionDetailViewState
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun allQuestions(): Flow<ResultState<QuestionsViewState>>
    fun voteQuestion(itemId: String?, vote: String = "up"): Flow<ResultState<QuestionDetailViewState>>
    fun answerQuestion(body: QuestionEntity.AnswerBody): Flow<ResultState<QuestionDetailViewState>>
}