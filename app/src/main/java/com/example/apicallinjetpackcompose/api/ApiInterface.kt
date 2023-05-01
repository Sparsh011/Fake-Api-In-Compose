package com.example.apicallinjetpackcompose.api

import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("posts")
    suspend fun makePostApiCall(
        @Header("Content-Type") contentType: String,
        @Body bodyForRequest: PostRequest
    ) : Response<PostApiCallResponse>

    @GET("albums/1/photos")
    suspend fun makeGetApiCall() : List<GetRequestApiResponseItem>

}