package com.example.cinemas

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.example.cinemas.data.CinemaData
import com.example.cinemas.data.FilmsDays
import com.example.cinemas.data.ItemRowModel
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
    val listMovie = mutableListOf<String>()
    var cinemaUrl = ""

    val listTime = mutableListOf<List<String>>()

    val info = mutableListOf<Triple<String, String, String>>()

    val listButtons = mutableListOf<FilmsDays>()



    val listRate = mutableListOf<ItemRowModel>()
    fun parsingDays(url: String){
        val runPars = runCatching {
            if (listButtons.isNotEmpty()) {
                listButtons.clear()
            }
            val doc: Document = Jsoup.connect(url).get()
            val titleDays: Elements =
                doc.getElementsByAttributeValue("class", "scheduleFilter_calendar week swipe outer-mobile inner-mobile")
            for(i in 0 until titleDays[0].childrenSize()){
                listButtons.add(
                    FilmsDays(
                        titleDays[0].child(i).attr("href"),
                        titleDays[0].child(i).child(0).text(),
                        titleDays[0].child(i).child(1).text()
                    )
                )
            }
        }
        runPars.onFailure {
            Log.e("Er", it.toString())
        }
    }
    fun parsingFilm(
        url: String,
        isFilms: Boolean = false){
        val runPars = runCatching {
            if (listURL.isNotEmpty()) {
                listURL.clear()
                info.clear()
                listTime.clear()
                listMovie.clear()
            }
            if (!isFilms) {
                val doc: Document = Jsoup.connect(url).get()
                val titleUrl: Elements =
                    doc.getElementsByAttributeValue("class", "scheduleAnons_more button-warning")
                cinemaUrl = titleUrl[0].attr("href")
            } else cinemaUrl = url
            val docMovie = Jsoup.connect(cinemaUrl).get()
            val titleImage: Elements =
                docMovie.getElementsByAttributeValue(
                    "class",
                    "showtimesMovie_poster picture picture-poster"
                )
            val titleInfo: Elements =
                docMovie.getElementsByAttributeValue("class", "showtimesMovie_info")
            val titleTime: Elements =
                docMovie.getElementsByAttributeValue("class", "showtimes_sessions")
            val titleMovie: Elements = docMovie.getElementsByAttributeValue("class", "showtimesMovie_link")
            titleMovie.forEach {
                listMovie.add(it.attr("href"))
            }
            titleImage.forEach {
                listURL.add(it.child(0).attr("srcset"))
            }
            titleInfo.forEach {
                info.add(Triple(it.child(0).text(), it.child(1).text(), it.child(2).text()))
            }
            titleTime.forEach {
                val timeList = mutableListOf<String>()
                it.getElementsByAttributeValue("class", "session_time").forEach { item ->
                    timeList.add(item.text())
                }
                listTime.add(timeList)
            }
        }
        runPars.onFailure {
            Log.e("Error", it.toString())
        }
    }


    suspend fun parsinRate(index: Int, cinemas: List<Pair<String, String>>) = withContext(Dispatchers.IO){
        launch {
            val docRate: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
            val titlesRating: Elements =
                docRate.getElementsByAttributeValue("class", "cinemaList_ref")
            if (listRate.size > cinemas.size) {
                listRate.clear()
            }
            var ratingofcinema: String

            var urlcinema: String

            val cinema = titlesRating[index]
            urlcinema = cinema.attr("href")
            val docofCinema = Jsoup.connect(urlcinema).get()
            val titleRate: Elements =
                docofCinema.getElementsByAttributeValue("class", "rating_inner")
            val titlePhone: Elements =
                docofCinema.getElementsByAttributeValue("class", "theaterInfo_phoneNumber")
            val image: Elements = docofCinema.getElementsByAttributeValue("class", "picture_image")
            ratingofcinema = titleRate[0].child(0).text()
            listRate.add(
                ItemRowModel(
                    image[0].attr("src"),
                    cinemas[index].first,
                    cinemas[index].second,
                    ratingofcinema,
                    true,
                    titlePhone[0].text(),
                    cinema.attr("href")
                )
            )
        }
    }
}