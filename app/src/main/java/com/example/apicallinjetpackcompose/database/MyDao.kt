package com.example.apicallinjetpackcompose.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apicallinjetpackcompose.api.GetRequestApiResponseItem
import com.example.apicallinjetpackcompose.api.PostApiCallResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM post_api_table")
    fun postRequestSavedResponse(): Flow<PostApiCallResponse>

    @Query("SELECT * FROM get_api_table")
    fun getRequestSavedResponse(): Flow<List<GetRequestApiResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetResponseItem(item: GetRequestApiResponseItem)

    @Delete
    suspend fun deleteGetResponse(item: GetRequestApiResponseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostResponse(item: PostApiCallResponse)

    @Delete
    suspend fun deletePostResponse(item: PostApiCallResponse)
}