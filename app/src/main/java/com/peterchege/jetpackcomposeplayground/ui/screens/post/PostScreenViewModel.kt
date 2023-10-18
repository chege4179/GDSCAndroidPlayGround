
package com.peterchege.jetpackcomposeplayground.ui.screens.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.repository.PostRepository
import com.peterchege.jetpackcomposeplayground.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PostScreenUiState {
    object Loading : PostScreenUiState

    data class Error(val message: String) : PostScreenUiState

    data class Success(val post:Post) : PostScreenUiState
}


class PostScreenViewModel (
    val repository: PostRepository,
    val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _uiState = MutableStateFlow<PostScreenUiState>(PostScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("postId")?.let {
            println("Post Id $it")
            getPostById(postId = it)
        }
    }


    fun getPostById(postId:String){
        viewModelScope.launch {
            val response = repository.getPostById(postId = postId)
            when (response){
                is NetworkResult.Success -> {
                    _uiState.value = PostScreenUiState.Success(response.data)

                }
                is NetworkResult.Error -> {
                    _uiState.value = PostScreenUiState.Error(message = response.message ?:" An unexpected error occurred")
                }
                is NetworkResult.Exception -> {
                    _uiState.value = PostScreenUiState.Error(message = response.e.message ?: "An unexpected exception occurred")

                }
            }
        }
    }
}