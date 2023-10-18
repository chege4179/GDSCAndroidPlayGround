
package com.peterchege.jetpackcomposeplayground.ui.screens.all_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.repository.PostRepository
import com.peterchege.jetpackcomposeplayground.util.NetworkResult

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface AllPostScreenUiState {
    object Loading : AllPostScreenUiState

    data class Error(val message: String) : AllPostScreenUiState

    data class Success(
        val posts: List<Post>,

    ) : AllPostScreenUiState
}


class AllPostsScreenViewModel(
    val repository: PostRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<AllPostScreenUiState>(AllPostScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllPosts()
    }

    fun getAllPosts(){
        viewModelScope.launch {
            val response = repository.getAllPosts()
            when (response){
                is NetworkResult.Success -> {
                    _uiState.value = AllPostScreenUiState.Success(response.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = AllPostScreenUiState.Error(message = response.message ?: "An unexpected error occurred")
                }
                is NetworkResult.Exception -> {
                    _uiState.value = AllPostScreenUiState.Error(message = response.e.message ?: "An unexpected exception occurred")
                }
            }
        }
    }
}