package com.buchi.question.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.buchi.core.utils.ResultState
import com.buchi.question.data.QuestionRepository
import com.buchi.question.model.QuestionEntity
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionDetailViewModel @Inject constructor(
    private val questionRepo: QuestionRepository
) : ViewModel() {

    private val stateChannel = ConflatedBroadcastChannel<QuestionDetailViewState>()
    private val eventChannel =
        ConflatedBroadcastChannel<QuestionDetailStateEvent>(QuestionDetailStateEvent.Idle())

    val viewState = stateChannel.asFlow().asLiveData()
    val dataState = eventChannel.asFlow()
        .flatMapLatest { stateEvent ->
            processActions(stateEvent)
        }
        .asLiveData()

    private fun processActions(stateEvent: QuestionDetailStateEvent): Flow<ResultState<QuestionDetailViewState>> {
        return when (stateEvent) {
            is QuestionDetailStateEvent.UpVoteQuestion -> {
                questionRepo.voteQuestion(
                    stateEvent.itemId,
                    vote = if (stateEvent.vote == 0) "up" else "down"
                )
            }
            is QuestionDetailStateEvent.AnswerQuestion -> {
                questionRepo.answerQuestion(stateEvent.body)
            }
            is QuestionDetailStateEvent.Idle -> {
                flow { emit(ResultState.data(QuestionDetailViewState())) }
            }
        }
    }

    fun setViewState(viewState: QuestionDetailViewState) {
        stateChannel.offer(viewState)
    }

    fun upVoteQuestion(item: QuestionEntity.Question) {
        eventChannel.offer(QuestionDetailStateEvent.UpVoteQuestion(item._id))
    }

    fun answerQuestion(item: QuestionEntity.Question, answer: String) {
        val body = QuestionEntity.AnswerBody(item._id, answer)
        eventChannel.offer(QuestionDetailStateEvent.AnswerQuestion(body))
    }
}