package com.peterchege.jetpackcomposeplayground.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)