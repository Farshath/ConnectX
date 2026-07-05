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
import com.farshath.connectx.ui.components.PostItem
import com.farshath.connectx.ui.viewmodel.HomeViewModel
import com.farshath.connectx.utils.Resource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCreatePost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val postsState by viewModel.postsState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ConnectX") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreatePost) {
                Icon(Icons.Default.Add, contentDescription = "Create Post")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (postsState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    val posts = postsState.data ?: emptyList()
                    if (posts.isEmpty()) {
                        Text(
                            text = "No posts yet. Be the first to post!",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(posts) { post ->
                                PostItem(
                                    post = post,
                                    onLikeClick = { viewModel.likePost(post.id, "") }
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = postsState.message ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
