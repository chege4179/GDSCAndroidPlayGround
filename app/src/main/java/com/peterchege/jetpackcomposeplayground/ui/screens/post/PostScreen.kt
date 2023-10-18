
package com.peterchege.jetpackcomposeplayground.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.jetpackcomposeplayground.ui.components.ErrorComponent
import com.peterchege.jetpackcomposeplayground.ui.components.LoadingComponent


@Composable
fun PostScreen(
    viewModel: PostScreenViewModel,
    postId:String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = postId){
        viewModel.getPostById(postId)
    }
    PostScreenContent(
        uiState = uiState,
        retryCallback = {  }
    )


}

@Composable
fun PostScreenContent(
    uiState:PostScreenUiState,
    retryCallback:() -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        when (uiState) {
            is PostScreenUiState.Loading -> {
                LoadingComponent()
            }

            is PostScreenUiState.Error -> {
                ErrorComponent(
                    retryCallback = { retryCallback() },
                    errorMessage = uiState.message
                )
            }

            is PostScreenUiState.Success -> {
                Text(text = "Id : ${uiState.post.id}")
                Text(text = "Title : ${uiState.post.title}")
                Text(text = "Body : ${uiState.post.body}")
                Text(text = "User ID : ${uiState.post.userId}")
            }
        }
    }


}

