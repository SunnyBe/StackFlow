package com.buchi.stackflow.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.data.AuthRepository
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signin.SignInViewModel
import com.buchi.stackflow.auth.presentation.signin.SignInViewState
import com.buchi.stackflow.utils.MainCoroutineScopeRule
import com.buchi.stackflow.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var authRepo: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SignInViewModel(authRepo)
    }

    @Test
    fun signIn_SuccessUpdatesViewStateAndDataState() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(
                    ResultState.data(
                        SignInViewState(
                            signInResponse = AuthEntity.AuthResponse(
                                error = false,
                                code = 200,
                                data = "some random id",
                                message = "user successfully logged in"
                            )
                        )
                    )
                )
            }
            // Process
            val signInBody = AuthEntity.SignInBody("username", "password")
            viewModel.signIn(signInBody.email!!, signInBody.password!!)
            Mockito.`when`(authRepo.signIn(signInBody)).thenReturn(testFlow)
            // Test
            val expectedString = "user successfully logged in"
            Assert.assertEquals(
                expectedString,
                viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.signInResponse?.message
            )
        }
    }
}
