package com.example.cinemas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.cinemas.data.FilmsDays
import com.example.cinemas.data.ShowingFilms
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CinemaInfo(name: String,
               address: String,
               phone: String,
               url: String,
               cinemaurl: String,
               navController: NavController
           ) {
    val isgetdata = remember {
        mutableStateOf(false)
    }

    runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            Parsing.parsingFilm(cinemaurl)
            Parsing.parsingDays(cinemaurl)
            isgetdata.value = true
        }
    }


    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 70.dp)) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        color = Color(0xFFCD250E),
                        width = 2.dp
                    )
            ) {
                   AsyncImage(
                       url,
                       placeholder = painterResource(R.drawable.profile),
                       contentDescription = "image",
                       contentScale = ContentScale.Fit,
                       modifier = Modifier
                           .padding(3.dp)
                           .size(64.dp)
                           .clip(RoundedCornerShape(10.dp)),
                       error = painterResource(R.drawable.profile),
                   )
                   Spacer(modifier = Modifier.width(4.dp))
                   Text(
                       text = name,
                       fontSize = 40.sp,
                       modifier = Modifier.padding(11.dp)
                   )
               }
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .border(
                           color = Color(0xFFCD250E),
                           width = 2.dp
                       )
               ) {
                   Text(
                       "Адрес",
                       fontSize = 23.sp,
                       modifier = Modifier.padding(12.dp)
                   )
                   Text(
                       address,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                color = Color(0xFFCD250E),
                                width = 2.dp
                            )
                    ) {
                        Text(
                            "Номер\nтелефона",
                            fontSize = 22.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                        Text(
                            phone,
                            fontSize = 21.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp).horizontalScroll(rememberScrollState())) {
                    Parsing.listButtons.forEach {
                        Days(it, isgetdata)
                    }
                }
            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))


        }
                val showingFilms = mutableListOf<ShowingFilms>()
                for (i in 0 until Parsing.listURL.size){
                    showingFilms.add(
                        ShowingFilms(
                            Parsing.listURL[i],
                            Parsing.listTime[i],
                            Parsing.info[i]
                        )
                    )
                }
                showingFilms.shuffle()
                itemsIndexed(if (isgetdata.value) showingFilms else emptyList()){_, item ->
                    Films(item, navController)
                }
            }

}


@ExperimentalLayoutApi
@Composable
fun Films(
    showingFilms: ShowingFilms,
    navController: NavController
){
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(5.dp)
            .clickable{
                navController.navigate("movie/${showingFilms.url
                    .replace(oldValue = "/", newValue = "[")}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)) {
            AsyncImage(
                model = showingFilms.url,
                contentDescription = "movie",
                placeholder = painterResource(R.drawable.camera),
                modifier = Modifier
                    .size(
                        width = 120.dp,
                        height = 180.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .padding(3.dp)
            )
            Column(
                Modifier
                    .padding(bottom = 2.dp, start = 3.dp)
                    .weight(1f)
            ) {
                Text(
                    text = showingFilms.info.first,
                    fontSize = 18.sp,
                    modifier = Modifier.width(140.dp),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
                Text(
                    text = showingFilms.info.second,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.width(100.dp),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
                Text(
                    text = showingFilms.info.third,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Box(contentAlignment = Alignment.TopEnd) {
                FlowRow(
                    modifier = Modifier.size(
                        width = 85.dp,
                        height = 150.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Отступ между элементами
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 0 until showingFilms.time.size) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFE789))
                                .padding(3.dp)
                        ) {
                            Text(
                                text = showingFilms.time[i],
                                fontSize = 18.sp,
                                color = Color(0xFF000000)
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Days(filmsDays: FilmsDays, isget: MutableState<Boolean>){
    Column(Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = filmsDays.dayoftheweek,
            fontSize = 17.sp)
        Button(onClick = {
            runBlocking {
                CoroutineScope(Dispatchers.IO).launch {
                    isget.value = false
                    Parsing.parsingFilm(filmsDays.url, true)
                    isget.value = true
                }
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCD250E), // Основной цвет
                contentColor = Color.White // Цвет содержимого
            )) {
            Text(text = filmsDays.num,
                fontSize = 16.sp)

        }
    }
}