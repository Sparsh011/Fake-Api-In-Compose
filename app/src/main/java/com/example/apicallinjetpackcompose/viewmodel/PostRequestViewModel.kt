package com.example.apicallinjetpackcompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallinjetpackcompose.api.ApiModelItem
import com.example.apicallinjetpackcompose.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostRequestViewModel : ViewModel() {
    var responseObserver = MutableLiveData<ApiModelItem>()
    private val apiService = ApiService()
    private val TAG = "PostRequestViewModel"

    init {
        val fakeBody = ApiModelItem("", 1, "", 1)
        responseObserver.value = fakeBody // Provide a default value
    }

    fun makeApiCall(title: String, body: String){
        viewModelScope.launch(Dispatchers.IO) {
            val res = apiService.makeApiCall(title, body).body()
            responseObserver.postValue(res)
        }
    }
}