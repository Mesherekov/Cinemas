package com.example.cinemas

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

@Suppress("DEPRECATED_IDENTITY_EQUALS")
fun parsing(): List<Pair<String, String>> {
    try {
        val doc: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
        val titlesCinema: Elements = doc.getElementsByAttributeValue("class", "cinemaList_info")
        val nameCinema = mutableListOf<String>()
        val nameAddress = mutableListOf<String>()
        titlesCinema.forEach{
            nameCinema.add(it.child(0).text())
            nameAddress.add(it.child(1).text())
        }
        val resCinema: List<Pair<String, String>> = nameCinema.zip(nameAddress)
        return resCinema
    } catch (ex: Exception) {
        Log.e("ErrorOFParsing", ex.toString())
    }

    return emptyList()
}
suspend fun parsingofcinema():  CinemaData = withContext(Dispatchers.IO) {

    try {
        val ratingofcinema = mutableListOf<String>()
        val urlimage = mutableListOf<String>()
        val urlcinema = mutableListOf<String>()
        val phone = mutableListOf<String>()
        launch {
            val doc: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
            val titlesRating: Elements =
                doc.getElementsByAttributeValue("class", "cinemaList_ref")
            titlesRating.forEach {
                urlcinema.add(it.attr("href"))
                val docofCinema = Jsoup.connect(it.attr("href")).get()
                val titleRate: Elements =
                    docofCinema.getElementsByAttributeValue("class", "rating_inner")
                val titlePhone: Elements = docofCinema.getElementsByAttributeValue("class", "theaterInfo_phoneNumber")
                val image : Elements = docofCinema.getElementsByAttributeValue("class", "picture_image")
                phone.add(titlePhone[0].text())
                urlimage.add(image[0].attr("src"))
                ratingofcinema.add(titleRate[0].child(0).text())
            }
        }
        return@withContext CinemaData(emptyList(), ratingofcinema, urlimage, urlcinema, phone)

    } catch (ex: Exception) {
        Log.e("ErrorOFParsing", ex.toString())
    }
    return@withContext CinemaData(emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
}