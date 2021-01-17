package com.buchi.question.presentation.detail

import androidx.lifecycle.ViewModel
import com.buchi.question.data.QuestionRepository
import javax.inject.Inject

class QuestionDetailViewModel @Inject constructor(
    val questionRepo: QuestionRepository
) : ViewModel() {

}