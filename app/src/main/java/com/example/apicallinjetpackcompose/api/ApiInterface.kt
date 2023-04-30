package com.example.apicallinjetpackcompose.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("posts")
    suspend fun makeApiCall(
        @Header("Content-Type") contentType: String,
        @Body bodyForRequest: PostRequest
    ) : Response<ApiModelItem>
}