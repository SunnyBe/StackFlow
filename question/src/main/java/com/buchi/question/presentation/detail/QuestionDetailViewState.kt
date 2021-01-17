package com.buchi.question.presentation.detail

import com.buchi.question.model.QuestionEntity

data class QuestionDetailViewState(
    val upVoted: QuestionEntity.QuestionResponse<String>? = null,
    val answeredQuestion: QuestionEntity.QuestionResponse<String>? = null
)