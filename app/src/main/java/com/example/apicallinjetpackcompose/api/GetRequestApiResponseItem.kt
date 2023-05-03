package com.example.apicallinjetpackcompose.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "get_api_table")
data class GetRequestApiResponseItem(
    @ColumnInfo(name = "album_id") val albumId: Int,
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "thumbnail_url")  val thumbnailUrl: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String
)