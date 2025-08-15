package com.example.cinemas


import org.jsoup.Jsoup

class MovieInfo(val url: String) {
    lateinit var movieData: MovieData

    fun parsing(){
        val doc = Jsoup.connect(url).get()

    }
}
data class MovieData(
    val name: String,
    val image: String,
    val filminfo: String,
    val rate: String,
    val tickets: String,
    val tags: String
)