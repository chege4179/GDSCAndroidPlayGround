package com.peterchege.jetpackcomposeplayground.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "post")
data class PostEntity(
    val userId:Int,
    @PrimaryKey
    val id:Int,
    val title:String,
    val body:String
)