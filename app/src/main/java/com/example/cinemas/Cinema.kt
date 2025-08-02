package com.example.cinemas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
           url: String
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
            Text("Номер телефона",
                fontSize = 22.sp,
                modifier = Modifier.padding(12.dp))
            Text(phone,
                fontSize = 21.sp,
                modifier = Modifier.padding(12.dp))
        }
    }
}