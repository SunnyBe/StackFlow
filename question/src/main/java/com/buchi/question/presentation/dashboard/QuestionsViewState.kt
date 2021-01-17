package com.buchi.question.presentation.dashboard

import com.buchi.question.model.QuestionEntity

data class QuestionsViewState(
    val allQuestions: QuestionEntity.QuestionListResponse? = null
)