package com.example.newsorgapi

import com.example.newsapiretrofit.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASEURL = "https://newsapi.org/"
const val apikey = "e8d03eff5bdc4e899df8a2668fa34d5e"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$apikey")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int) : Call<News>

}

object NewsService {

    val newsInstance : NewsInterface

init {

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    newsInstance = retrofit.create(NewsInterface::class.java)
}
}