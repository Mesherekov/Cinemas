package com.example.cinemas


import org.jsoup.Jsoup

class MovieInfo(val url: String) {
    lateinit var movieData: MovieData

    fun parsing(){
        val doc = Jsoup.connect(url).get()
        var titles = doc.getElementsByAttributeValue("class", "picture picture-poster")
        movieData.image = titles[0].child(0).attr("scrset")
        titles = doc.getElementsByAttributeValue("class", "newFilmInfo_ticketsBtn button-large button-warning as-desktop")
        movieData.tickets = Pair(titles[0].text(), titles[0].attr("href"))
        titles = doc.getElementsByAttributeValue("class", "newFilmInfo_title")
        movieData.name = titles[0].text()
        titles = doc.getElementsByAttributeValue("class", "newFilmInfo_age")
        movieData.age = titles[0].text()
        titles = doc.getElementsByAttributeValue("class", "newFilmInfo_genreMenu swipe outer-mobile inner-mobile")
        val listTag = mutableListOf<String>()
        titles[0].forEach {
            listTag.add(it.text())
        }
        movieData.tags = listTag
        titles = doc.getElementsByAttributeValue("class", "styles_paragraph__wEGPz")
        movieData.filminfo = titles[0].text()
        titles = doc.getElementsByAttributeValue("class", "ratingBlockCard_local")
        movieData.rate = titles[0].text()
    }
}
data class MovieData(
    var name: String,
    var image: String,
    var filminfo: String,
    var rate: String,
    var tickets: Pair<String, String>,
    var tags: List<String>,
    var age: String
)