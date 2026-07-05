package com.farshath.connectx.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.farshath.connectx.ui.components.NotificationItem
import com.farshath.connectx.ui.viewmodel.NotificationsViewModel
import com.farshath.connectx.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val notificationsState by viewModel.notificationsState

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notifications") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (notificationsState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    val notifications = notificationsState.data ?: emptyList()
                    if (notifications.isEmpty()) {
                        Text(
                            text = "No notifications yet",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(notifications) { notification ->
                                NotificationItem(
                                    notification = notification,
                                    onClick = { viewModel.markAsRead(notification.id) }
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = notificationsState.message ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
