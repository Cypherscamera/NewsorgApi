package com.example.newsapiretrofit

data class Article(val author: String,
                   val title: String,
                   val description: String,
                   val url: String,
                   val urlToImage : String) {
}