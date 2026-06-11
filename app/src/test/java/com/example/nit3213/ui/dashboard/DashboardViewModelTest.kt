package com.example.nit3213.ui.dashboard

import com.example.nit3213.data.model.DashboardResponse
import com.example.nit3213.data.model.Entity
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
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private val repository: AuthRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch dashboard success sets success state with entities`() = runTest {
        val entities = listOf(
            Entity("Phone", "Apple", "iOS", 2023, "Desc")
        )
        val dashboardResponse = DashboardResponse(entities, 1)
        coEvery { repository.getDashboard(any()) } returns Response.success(dashboardResponse)

        viewModel.fetchDashboard("test_key")
        
        advanceUntilIdle()

        assertTrue(viewModel.dashboardState.value is DashboardState.Success)
        val successState = viewModel.dashboardState.value as DashboardState.Success
        assertTrue(successState.entities.size == 1)
        assertTrue(successState.entities[0].deviceName == "Phone")
    }

    @Test
    fun `fetch dashboard failure sets error state`() = runTest {
        coEvery { repository.getDashboard(any()) } returns Response.error(500, mockk(relaxed = true))

        viewModel.fetchDashboard("test_key")
        
        advanceUntilIdle()

        assertTrue(viewModel.dashboardState.value is DashboardState.Error)
    }
}
