package com.buchi.stackflow.auth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buchi.core.di.ViewModelKey
import com.buchi.core.utils.ViewModelFactory
import com.buchi.stackflow.auth.data.AuthRepository
import com.buchi.stackflow.auth.data.AuthRepositoryImpl
import com.buchi.stackflow.auth.presentation.AuthViewModel
import com.buchi.stackflow.auth.presentation.signin.SignInViewModel
import com.buchi.stackflow.auth.presentation.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ApplicationComponent::class)
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel
}

@Module
@InstallIn(ApplicationComponent::class)
object AuthModule {
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}
