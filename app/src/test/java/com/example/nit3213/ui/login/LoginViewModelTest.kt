package com.example.nit3213.ui.login

import com.example.nit3213.data.model.AuthRequest
import com.example.nit3213.data.model.AuthResponse
import com.example.nit3213.data.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val repository: AuthRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success sets success state`() = runTest {
        val authResponse = AuthResponse("test_keypass")
        coEvery { repository.login(any()) } returns Response.success(authResponse)

        viewModel.login("s8171155", "Sudan")
        
        // Advance dispatcher to execute coroutine
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is LoginState.Success)
        val successState = viewModel.loginState.value as LoginState.Success
        assertTrue(successState.keypass == "test_keypass")
    }

    @Test
    fun `login failure sets error state`() = runTest {
        coEvery { repository.login(any()) } returns Response.error(401, mockk(relaxed = true))

        viewModel.login("Wrong", "Password")
        
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is LoginState.Error)
    }
}
