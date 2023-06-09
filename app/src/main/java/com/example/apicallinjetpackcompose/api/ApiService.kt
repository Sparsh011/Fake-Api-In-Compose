package com.example.apicallinjetpackcompose.api

import androidx.lifecycle.LiveData
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val api = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    suspend fun makeApiCall(title: String, body: String): Response<PostApiCallResponse> {
        val requestBody = PostRequest(title, body, 1)
        return api.makePostApiCall("application/json; charset=UTF-8", requestBody)
    }

    suspend fun makeGetApiCall(): List<GetRequestApiResponseItem> {
        return api.makeGetApiCall()
    }
}