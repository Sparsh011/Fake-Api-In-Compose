package com.example.apicallinjetpackcompose.api

data class PostApiCallResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)