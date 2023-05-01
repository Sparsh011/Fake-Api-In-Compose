package com.example.apicallinjetpackcompose.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallinjetpackcompose.api.PostApiCallResponse
import com.example.apicallinjetpackcompose.api.ApiService
import com.example.apicallinjetpackcompose.api.GetRequestApiResponse
import com.example.apicallinjetpackcompose.api.GetRequestApiResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NiceViewModel : ViewModel() {
    var postRequestObserver = MutableLiveData<PostApiCallResponse>()
    private val apiService = ApiService()
    private val TAG = "PostRequestViewModel"
    var getRequestObserver = MutableLiveData<List<GetRequestApiResponseItem>>()


    fun makeApiCall(title: String, body: String){
        viewModelScope.launch(Dispatchers.IO) {
            val res = apiService.makeApiCall(title, body).body()
            postRequestObserver.postValue(res)
        }
    }

    fun makeGetApiCall() {
        viewModelScope.launch (Dispatchers.IO){
            val res = apiService.makeGetApiCall()
            getRequestObserver.postValue(res)
            Log.d(TAG, "makeGetApiCall: Made!")
        }
    }
}