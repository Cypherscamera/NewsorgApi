package com.example.newsorgapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapiretrofit.Article
import com.example.newsapiretrofit.News
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Mainviewmodel : ViewModel(){

    lateinit var liveDataList : MutableLiveData<List<Article>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataobserver() : MutableLiveData<List<Article>>{

        return liveDataList
    }

    var selectedcountry = "in"

    fun makeApiCall(){

        val news: Call<News> = NewsService.newsInstance.getHeadlines(selectedcountry, 1)
        news.enqueue(object : retrofit2.Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val articles = response.body()
                if (articles != null) {
                    liveDataList.postValue(articles.articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("Sad", "Error")
            }

        })
    }

}