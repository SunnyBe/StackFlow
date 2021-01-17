package com.buchi.question.data

import com.buchi.core.utils.OkHttpHelper
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    val okHttpClient: OkHttpHelper
) : QuestionRepository {

}