package com.example.cinemas


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : ComponentActivity(){
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.inter18, FontWeight.Normal)
        )
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                parsing()
            }
        }


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


     fun parsing(){
           try {
               val doc: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()

           } catch (ex: Exception) {
               Log.i("MAxeer", ex.toString())
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
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },

                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = item.icon, contentDescription = item.label)

                            Text(
                                text = item.label,
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
        PersonProfile()
    }
    @Composable
    fun PersonProfile(){
        Column(modifier = Modifier.fillMaxWidth()) {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd) {
                    Icon(
                        modifier = Modifier
                            .size(70.dp)
                            .clickable {

                            },
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings"
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "person",
                        modifier = Modifier.size(60.dp),
                    )

                    Spacer(Modifier.width(7.dp))
                    Text(
                        text = "Max",
                        fontSize = 30.sp,
                        maxLines = 2,
                    )
                }
            }
        }
    }

}
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
    val badgeCount: Int = 0
)