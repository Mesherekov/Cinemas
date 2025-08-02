@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.example.cinemas


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemas.ui.theme.Navigation

class MainActivity : ComponentActivity(){
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?){
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
                if ((checkSelfPermission(Manifest.permission.INTERNET)
                            !== PackageManager.PERMISSION_GRANTED)
                ) {
                    requestPermissions(arrayOf(Manifest.permission.INTERNET), 1)
                }
                Navigation(navController = navController)
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
                    }
                )
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
