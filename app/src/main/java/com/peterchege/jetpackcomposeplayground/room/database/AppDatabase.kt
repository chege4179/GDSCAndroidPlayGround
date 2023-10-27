package com.peterchege.jetpackcomposeplayground.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peterchege.jetpackcomposeplayground.room.dao.PostDao
import com.peterchege.jetpackcomposeplayground.room.entities.PostEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        PostEntity::class
    ]
)
abstract class AppDatabase :RoomDatabase(){

    abstract val postDao:PostDao


}