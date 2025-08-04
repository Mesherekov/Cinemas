package com.example.cinemas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun Cinema(name: String,
           address: String,
           phone: String,
           url: String,
           cinemaurl: String
           ) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(
                color = Color.LightGray,
                width = 2.dp
            )) {
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
            Text(text = name,
                fontSize = 40.sp,
                modifier = Modifier.padding(11.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(color = Color.LightGray,
                width = 2.dp)) {
            Text("Адрес",
                fontSize = 23.sp,
                modifier = Modifier.padding(12.dp))
            Text(address,
                fontSize = 25.sp,
                modifier = Modifier.padding(12.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(color = Color.LightGray,
                width = 2.dp)) {
            Text("Номер\nтелефона",
                fontSize = 22.sp,
                modifier = Modifier.padding(12.dp))
            Text(phone,
                fontSize = 21.sp,
                modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun Movie(
    movieSingle: MovieSingle
){
    Column {
        AsyncImage(
            model = movieSingle.url,
            contentDescription = "movie",
            placeholder = painterResource(R.drawable.camera)
        )
        Text(text = movieSingle.numofsessions,
            fontSize = 16.sp,
            color = Color.LightGray)
        LazyRow {
            itemsIndexed(movieSingle.time){ _, item->
                Box(modifier = Modifier.background(Color.Yellow)) {
                    Text(text = item, fontSize = 18.sp)
                }
            }
        }
    }
}