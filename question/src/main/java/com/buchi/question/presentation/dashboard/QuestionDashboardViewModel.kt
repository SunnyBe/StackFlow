package com.buchi.question.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.buchi.core.utils.ResultState
import com.buchi.question.data.QuestionRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionDashboardViewModel @Inject constructor(
    private val questionRepo: QuestionRepository
) : ViewModel() {

    private val stateChannel = ConflatedBroadcastChannel<QuestionsViewState>()
    private val eventChannel =
        ConflatedBroadcastChannel<QuestionsStateEvent>(QuestionsStateEvent.Idle())

    val viewState = stateChannel.asFlow().asLiveData()
    val dataState = eventChannel.asFlow()
        .flatMapLatest { stateEvent ->
            processActions(stateEvent)
        }
        .asLiveData()

    private fun processActions(stateEvent: QuestionsStateEvent): Flow<ResultState<QuestionsViewState>> {
        return when (stateEvent) {
            is QuestionsStateEvent.AllQuestions -> {
                questionRepo.allQuestions()
            }
            is QuestionsStateEvent.Idle -> {
                flow { emit(ResultState.data(QuestionsViewState())) }
            }
        }
    }

    fun setViewState(viewState: QuestionsViewState) {
        stateChannel.offer(viewState)
    }

    fun fetchAllQuestions() {
        eventChannel.offer(QuestionsStateEvent.AllQuestions())
    }

}