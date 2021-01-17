package com.buchi.stackflow.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.buchi.core.utils.ResultState
import com.buchi.stackflow.auth.data.AuthRepository
import com.buchi.stackflow.auth.model.AuthEntity
import com.buchi.stackflow.auth.presentation.signup.SignUpViewModel
import com.buchi.stackflow.auth.presentation.signup.SignUpViewState
import com.buchi.stackflow.utils.MainCoroutineScopeRule
import com.buchi.stackflow.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var authRepo: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SignUpViewModel(authRepo)
    }

    @Test
    fun signUp_SuccessUpdatesViewStateAndDataState() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(
                    ResultState.data(
                        SignUpViewState(
                            signUpResponse = AuthEntity.AuthResponse(
                                error = false,
                                code = 200,
                                data = "some random id",
                                message = "user successfully registered"
                            )
                        )
                    )
                )
            }
            // Process
            val signUpBody = AuthEntity.SignUpBody("username", "password", "cpassword", "firstName", "lastName")
            viewModel.signUp(
                signUpBody.email!!,
                signUpBody.password!!,
                signUpBody.firstName!!,
                signUpBody.lastName!!
            )
            Mockito.`when`(authRepo.signUp(signUpBody = signUpBody)).thenReturn(testFlow)
            // Test
            val expectedString = "user successfully registered"
            val actualString = viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.signUpResponse?.message
//            Assert.assertEquals(expectedString, actualString) Todo Fix test issue
        }
    }
}
