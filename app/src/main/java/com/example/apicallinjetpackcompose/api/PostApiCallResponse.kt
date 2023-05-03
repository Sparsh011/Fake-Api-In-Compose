package com.example.apicallinjetpackcompose.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_api_table")
data class PostApiCallResponse(
    @ColumnInfo(name = "body") val body: String,
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "age") val title: String,
    @ColumnInfo(name = "userId") val userId: Int
)