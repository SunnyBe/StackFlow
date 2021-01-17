package com.buchi.question.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.buchi.question.data.QuestionRepository
import javax.inject.Inject

class QuestionDashboardViewModel @Inject constructor(
    val questionRepo: QuestionRepository
) : ViewModel() {


}