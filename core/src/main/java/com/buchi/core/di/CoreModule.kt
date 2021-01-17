package com.buchi.core.di

import androidx.lifecycle.ViewModelProvider
import com.buchi.core.utils.OkHttpHelper
import com.buchi.core.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class CoreViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun provideOkhttpHelper() = OkHttpHelper("https://stackoverflw.herokuapp.com/v1/")
}

