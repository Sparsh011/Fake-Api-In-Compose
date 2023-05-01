package com.example.apicallinjetpackcompose.api

data class GetRequestApiResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)