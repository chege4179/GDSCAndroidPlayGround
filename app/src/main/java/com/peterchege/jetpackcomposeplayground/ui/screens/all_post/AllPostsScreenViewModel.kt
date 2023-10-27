
package com.peterchege.jetpackcomposeplayground.ui.screens.all_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.mappers.toEntity
import com.peterchege.jetpackcomposeplayground.mappers.toExternalModel
import com.peterchege.jetpackcomposeplayground.repository.PostRepository
import com.peterchege.jetpackcomposeplayground.room.database.AppDatabase
import com.peterchege.jetpackcomposeplayground.util.NetworkResult

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface AllPostScreenUiState {
    object Loading : AllPostScreenUiState

    data class Error(val message: String) : AllPostScreenUiState

    data class Success(
        val posts: List<Post>,

    ) : AllPostScreenUiState
}


class AllPostsScreenViewModel(
    val repository: PostRepository,
    val database:AppDatabase,
): ViewModel() {

    val uiState = database.postDao.getAllPosts()
        .map {
            AllPostScreenUiState.Success(it.map { it.toExternalModel() })
        }
        .catch { AllPostScreenUiState.Error("An unexpected error occurred") }
        .onStart { AllPostScreenUiState.Loading }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AllPostScreenUiState.Loading
        )

    init {
        getAllPosts()
    }

    fun getAllPosts(){
        viewModelScope.launch {
            val response = repository.getAllPosts()
            when (response){
                is NetworkResult.Success -> {

                    database.postDao.deleteAllPosts()
                    database.postDao.insertPosts(response.data.map { it.toEntity() })
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }
}