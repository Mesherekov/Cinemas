package com.example.cinemas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.inter18, FontWeight.Normal)
        )
        setContent {
            MainList()
        }
    }
    @Composable
    fun MainList(){
        Column {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            )
            {
                itemsIndexed(
                    listOf(
                        ItemRowModel(R.drawable.rer, "Pervomaisky", "Omsk", "4,7/5"),
                        ItemRowModel(R.drawable.rer, "Ben", "Moskow", "4/5"),
                        ItemRowModel(R.drawable.rer, "Kate", "Omsk", "3,2/5"),
                        ItemRowModel(R.drawable.rer, "John", "Tomsk", "2,7/5"),
                        ItemRowModel(R.drawable.rer, "Ben", "Novosybirsk", "2,5/5"),
                        ItemRowModel(R.drawable.rer, "Kate", "New-York", "4,9/5"),
                        ItemRowModel(R.drawable.rer, "John", "London", "1,8/5"),
                        ItemRowModel(R.drawable.rer, "Ben", "Omsk", "4,7/5"),
                        ItemRowModel(R.drawable.rer, "Kate", "Omsk", "4,7/5"),
                        ItemRowModel(R.drawable.rer, "John", "Omsk", "4,7/5")
                    )
                ) { _, item ->
                    ItemRow(item = item)
                }
            }

        }
    }
}
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)