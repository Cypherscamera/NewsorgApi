package com.example.newsapiretrofit

data class News(val status: String, val totalResults: Int, val articles: List<Article>) {
}