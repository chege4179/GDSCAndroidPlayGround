package com.peterchege.jetpackcomposeplayground.mappers

import com.peterchege.jetpackcomposeplayground.api.responses.Post
import com.peterchege.jetpackcomposeplayground.room.entities.PostEntity

fun PostEntity.toExternalModel():Post{
    return Post(
        id = id,
        title = title,
        body = body,
        userId = userId
    )
}

fun Post.toEntity():PostEntity{
    return PostEntity(
        id = id,
        title = title,
        body = body,
        userId = userId
    )
}