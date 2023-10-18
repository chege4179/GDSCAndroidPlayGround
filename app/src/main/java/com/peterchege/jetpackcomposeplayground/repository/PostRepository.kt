package com.peterchege.jetpackcomposeplayground.repository

import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.util.NetworkResult

interface PostRepository {

    suspend fun getAllPosts():NetworkResult<List<Post>>

    suspend fun getPostById(postId:String):NetworkResult<Post>
}