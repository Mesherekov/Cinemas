package com.example.cinemas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@SuppressLint("RememberReturnType")
@Composable
fun ItemRow(item: ItemRowModel){
    val fontFamily = FontFamily(
        Font(R.font.inter18, FontWeight.Normal)
    )
    val imageurl = remember {
        mutableStateOf(false)
    }
    val isTap = remember {
        mutableStateOf(false)
    }

    imageurl.value = item.dataget
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
        }
        .pointerInput(Unit) {

            detectTapGestures(onLongPress = {

                isTap.value = !isTap.value
            })

        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(3.dp)
        ) {

               AsyncImage(
                   item.imageurl  ,
                   placeholder = painterResource(R.drawable.profile),
                   contentDescription = "image",
                   contentScale = ContentScale.Fit,
                   modifier = Modifier
                       .padding(3.dp)
                       .size(64.dp)
                       .clip(RoundedCornerShape(10.dp)),
                   error = painterResource(R.drawable.profile),
                   onError = { errorResult ->
                       // This lambda is called when an error occurs
                       Log.e("CoilError", "Failed to load image: ${errorResult.result.throwable}")
                       // You can inspect errorResult.result.throwable for details
                   }
               )

            Column (Modifier.padding(4.dp)){
                Text(text = item.title,
                    fontSize = 19.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.width(170.dp))
                Text(text = item.city,
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.width(170.dp),
                    maxLines = if (isTap.value) 5 else 2,
                    )
            }

            Row(modifier = Modifier
                .weight(0.85f)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.End) {
                Image(painter = painterResource(id = R.drawable.star_rate),
                    contentDescription = "star_rate",
                    modifier = Modifier
                        .size(40.dp))
                Text(item.rate,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(top = 2.dp))
            }

        }
    }
}