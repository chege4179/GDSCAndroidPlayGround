/*
 * Copyright 2023 Json Placeholder App by Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.jetpackcomposeplayground.ui.screens.all_post

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.jetpackcomposeplayground.ui.components.ErrorComponent
import com.peterchege.jetpackcomposeplayground.ui.components.LoadingComponent
import com.peterchege.jetpackcomposeplayground.ui.components.PostCard


@Composable
fun AllPostsScreen(
    navigateToPostScreen: (String) -> Unit,
    viewModel: AllPostsScreenViewModel

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllPostsScreenContent(
        uiState = uiState,
        retryCallBack = { viewModel.getAllPosts() },
        onPostClick = { navigateToPostScreen(it) }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPostsScreenContent(
    uiState: AllPostScreenUiState,
    retryCallBack: () -> Unit,
    onPostClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Posts")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            when (uiState) {
                is AllPostScreenUiState.Loading -> {
                    LoadingComponent()
                }

                is AllPostScreenUiState.Error -> {
                    ErrorComponent(
                        retryCallback = { retryCallBack() },
                        errorMessage = uiState.message
                    )
                }

                is AllPostScreenUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                            .padding(it)
                    ) {

                        items(items = uiState.posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onPostClick = {
                                    onPostClick(it.id.toString())
                                }
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                        }

                    }
                }
            }
        }
    }


}