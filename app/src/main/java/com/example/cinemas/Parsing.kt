package com.example.cinemas

import android.util.Log
import com.example.cinemas.data.CinemaData
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
suspend fun parsingofcinema(): CinemaData = withContext(Dispatchers.IO) {

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

object Parsing{
    val listURL = mutableListOf<String>()
    var cinemaUrl = ""

    val listTime = mutableListOf<List<String>>()

    val info = mutableListOf<Triple<String, String, String>>()


    var ratingofcinema = ""
    var urlimage = ""
    var urlcinema = ""
    var phone = ""

    val docRate: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
    val titlesRating: Elements =
        docRate.getElementsByAttributeValue("class", "cinemaList_ref")


    fun parsingFilm(url: String){
        if(listURL.isNotEmpty()){
            listURL.clear()
            info.clear()
            listTime.clear()
        }
        val doc: Document = Jsoup.connect(url).get()
        val titleUrl: Elements = doc.getElementsByAttributeValue("class", "scheduleAnons_more button-warning")
        cinemaUrl = titleUrl[0].attr("href")
        val docMovie = Jsoup.connect(cinemaUrl).get()
        val titleImage: Elements =
            docMovie.getElementsByAttributeValue("class", "showtimesMovie_poster picture picture-poster")
        val titleInfo: Elements =
            docMovie.getElementsByAttributeValue("class", "showtimesMovie_info")
        val titleTime: Elements =
            docMovie.getElementsByAttributeValue("class", "showtimes_sessions")

        titleImage.forEach {
            listURL.add(it.child(0).attr("srcset")) }
        titleInfo.forEach {
            info.add(Triple(it.child(0).text(), it.child(1).text(), it.child(2).text()))
        }
        titleTime.forEach {
            val timeList = mutableListOf<String>()
            it.getElementsByAttributeValue("class", "session_time").forEach{item ->
                timeList.add(item.text())
            }
            listTime.add(timeList)
        }
    }

    fun parsinRate(index: Int){
        val cinema = titlesRating[index]
        urlcinema = cinema.attr("href")
        val docofCinema = Jsoup.connect(urlcinema).get()
        val titleRate: Elements =
            docofCinema.getElementsByAttributeValue("class", "rating_inner")
        val titlePhone: Elements = docofCinema.getElementsByAttributeValue("class", "theaterInfo_phoneNumber")
        val image : Elements = docofCinema.getElementsByAttributeValue("class", "picture_image")
        phone = titlePhone[0].text()
        urlimage = image[0].attr("src")
        ratingofcinema = titleRate[0].child(0).text()

    }
}