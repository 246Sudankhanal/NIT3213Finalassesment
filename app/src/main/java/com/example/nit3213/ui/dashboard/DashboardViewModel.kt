package com.example.nit3213.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213.data.model.Entity
import com.example.nit3213.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    fun fetchDashboard(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading
            try {
                val response = repository.getDashboard(keypass)
                if (response.isSuccessful) {
                    val entities = response.body()?.entities ?: emptyList()
                    _dashboardState.value = DashboardState.Success(entities)
                } else {
                    _dashboardState.value = DashboardState.Error("Failed to fetch dashboard: ${response.message()}")
                }
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error("An error occurred: ${e.message}")
            }
        }
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val entities: List<Entity>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}
