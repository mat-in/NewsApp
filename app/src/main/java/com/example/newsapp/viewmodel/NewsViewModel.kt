package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsInterface
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.data.RetrofitInstance
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    var newsLiveData = MutableLiveData<List<News>>()
    var errorLiveData = MutableLiveData<String>()
    var apiKey = "caee27927c5e4678a63d2268a81f9759"
    var retrofitService = RetrofitInstance()
        .getRetrofitInstance()
        .create(NewsInterface::class.java)
    fun getNews(){
        viewModelScope.launch {
            var response = retrofitService.getNews(apiKey)
            if (response.isSuccessful){
                newsLiveData.postValue(response.body()?.articles?: emptyList())
            }
            else{
                errorLiveData.postValue("Err: ${response.code()}")
            }
        }
    }
}