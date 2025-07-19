package com.example.cinemas


import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.inter18, FontWeight.Normal)
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavBar(items = listOf(
                        BottomNavItem(
                            "Home",
                            route = "home",
                            icon = Icons.Default.Home
                        ),
                        BottomNavItem(
                            "Profile",
                            route = "profile",
                            icon = Icons.Default.Person
                        )
                    ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                        )
                }
            ) {
                Navigation(navController = navController)
            }
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
                        ItemRowModel(R.drawable.rer, "John", "Omsk", "4,7/5"),
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
            containerColor = Color(242f, 221f, 198f),
            tonalElevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route==backStack.value?.destination?.route
                NavigationBarItem(selected = selected,
                    onClick = { onItemClick(item) },

                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = item.icon, contentDescription = item.label)

                                Text(text = item.label,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )

                        }
                    },
//                    colors = NavigationBarItemColors(
//                        selectedIconColor = Color.Blue,
//                        selectedTextColor = Color.Blue,
//                        selectedIndicatorColor = Color.Blue,
//                        Color.Gray,
//                        Color.Gray,
//                        Color.Gray,
//                        Color.Gray
//                    )
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