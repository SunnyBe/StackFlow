package com.buchi.question.presentation.detail

import com.buchi.question.model.QuestionEntity

sealed class QuestionDetailStateEvent {
    class Idle() : QuestionDetailStateEvent()
    class UpVoteQuestion(val itemId: String?, val vote: Int = 0) : QuestionDetailStateEvent()
    class AnswerQuestion(val body: QuestionEntity.AnswerBody) : QuestionDetailStateEvent()
}