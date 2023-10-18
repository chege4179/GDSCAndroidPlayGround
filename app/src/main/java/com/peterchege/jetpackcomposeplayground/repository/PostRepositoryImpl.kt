package com.peterchege.jetpackcomposeplayground.repository

import com.peterchege.jetpackcomposeplayground.api.ApiService
import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.api.safeApiCall
import com.peterchege.jetpackcomposeplayground.util.NetworkResult

class PostRepositoryImpl (
    val apiService: ApiService,
): PostRepository {
    override suspend fun getAllPosts(): NetworkResult<List<Post>> {
        return safeApiCall { apiService.getAllPosts() }
    }

    override suspend fun getPostById(postId:String): NetworkResult<Post> {
        return safeApiCall { apiService.getPostById(postId = postId) }
    }


}