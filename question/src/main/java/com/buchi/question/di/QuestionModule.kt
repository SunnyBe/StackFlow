package com.buchi.question.di

import androidx.lifecycle.ViewModel
import com.buchi.core.di.ViewModelKey
import com.buchi.core.utils.OkHttpHelper
import com.buchi.question.data.QuestionRepository
import com.buchi.question.data.QuestionRepositoryImpl
import com.buchi.question.presentation.QuestionViewModel
import com.buchi.question.presentation.dashboard.QuestionDashboardViewModel
import com.buchi.question.presentation.detail.QuestionDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class QuestionViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    abstract fun bindQuestionViewModel(viewModel: QuestionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionDashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: QuestionDashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionDetailViewModel::class)
    abstract fun bindQuestionDetailViewModel(viewModel: QuestionDetailViewModel): ViewModel
}

@Module
@InstallIn(ApplicationComponent::class)
object QuestionModule {
    @Provides
    @Singleton
    fun provideQuestionRepository(okhttp: OkHttpHelper): QuestionRepository = QuestionRepositoryImpl(okhttp)
}