package com.peterchege.jetpackcomposeplayground.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.jetpackcomposeplayground.room.entities.PostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(post:List<PostEntity>)

    @Query("SELECT * FROM post")
    fun getAllPosts(): Flow<List<PostEntity>>


    @Query("SELECT * FROM post WHERE id =:postId")
    fun getPostById(postId:Int):Flow<PostEntity?>

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()


}