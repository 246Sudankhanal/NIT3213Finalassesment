package com.example.nit3213.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nit3213.data.model.Entity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    keypass: String,
    viewModel: DashboardViewModel,
    onEntityClick: (Entity) -> Unit
) {
    val dashboardState by viewModel.dashboardState.collectAsState()

    LaunchedEffect(keypass) {
        viewModel.fetchDashboard(keypass)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Technology Dashboard") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (dashboardState) {
                is DashboardState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DashboardState.Success -> {
                    val entities = (dashboardState as DashboardState.Success).entities
                    LazyColumn {
                        items(entities) { entity ->
                            EntityItem(entity = entity, onClick = { onEntityClick(entity) })
                        }
                    }
                }
                is DashboardState.Error -> {
                    Text(
                        text = (dashboardState as DashboardState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EntityItem(entity: Entity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = entity.deviceName, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Manufacturer: ${entity.manufacturer}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "OS: ${entity.operatingSystem}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
