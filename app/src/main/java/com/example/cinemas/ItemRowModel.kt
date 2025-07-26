package com.example.cinemas

import android.graphics.drawable.Drawable

data class ItemRowModel(
    val imageId: Drawable,
    val title: String,
    val city: String,
    var rate: String
)
