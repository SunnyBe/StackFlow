package com.buchi.question.presentation.dashboard

sealed class QuestionsStateEvent {
    class AllQuestions() : QuestionsStateEvent()
    class Idle() : QuestionsStateEvent()
}