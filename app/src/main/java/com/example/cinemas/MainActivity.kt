package com.example.cinemas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.inter18, FontWeight.Normal)
        )
        setContent {
            HomeScreen()
        }
    }

    @Composable
    fun MainList() {
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


    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
    @Composable
    fun BottomNavBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ){
        val backStack = navController.currentBackStackEntryAsState()
        NavigationBar(
            modifier = modifier,
            containerColor = Color.Gray,
            tonalElevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route==backStack.value?.destination?.route
                NavigationBarItem(selected = selected,
                    onClick = { onItemClick(item) },

                    icon = {

                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = Color.Blue,
                        selectedTextColor = Color.Blue,
                        selectedIndicatorColor = Color.Blue,
                        Color.Gray,
                        Color.Gray,
                        Color.Gray,
                        Color.Gray
                    )
                    )
            }
        }
    }

    @Composable
    fun HomeScreen() {
        MainList()
    }
    @Composable
    fun ProfileScreen() {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(text = "profile")
        }
    }

}
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
    val badgeCount: Int = 0
)