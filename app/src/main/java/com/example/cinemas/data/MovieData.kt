package com.example.cinemas.data

data class MovieData(
    val urlImage: List<String>,
    val time: List<List<String>>,
    val numberofsessions: List<String>,
    val Cinema: String?
)