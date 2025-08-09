package com.example.cinemas.data

data class ShowingFilms(
    val url: String,
    val time: List<String>,
    val info: Triple<String, String, String>
)