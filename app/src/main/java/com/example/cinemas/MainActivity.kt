package com.example.cinemas


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

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
                Navigation(navController = navController)
            }
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    @Composable
    fun MainList() {
        val isdataget = remember {
            mutableStateOf(false)
        }
        val isdataofevery = remember {
            mutableStateOf(false)
        }
        var cinemas: List<Pair<String, String>> = listOf()
        var cinemadata: CinemaData = CinemaData(emptyList(), emptyList(), emptyList())
//        val cinemaData: CinemaData by lazy {
//
//            var cd: CinemaData = CinemaData(emptyList(), emptyList(), emptyList())
//            runBlocking {
//                CoroutineScope(Dispatchers.IO).launch{
//                    cd = parsingofcinema()
//                    isdataofevery.value = true
//
//                }
//            }
//            cd
//        }
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                cinemas =  parsing()
                isdataget.value = true
                cinemadata = parsingofcinema()
                isdataofevery.value = true
            }
        }



        Column {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            )
            {
                val indexCinema = mutableListOf<ItemRowModel>()
                for (i in 0 until cinemas.size){
                    indexCinema.add(ItemRowModel(
                        if (isdataofevery.value)
                        cinemadata.urlimage[i] else "",
                        cinemas[i].first,
                        cinemas[i].second,
                        if (isdataofevery.value) {
                            if (cinemadata.rate[i].lowercase() != "мало голосов") {
                                cinemadata.rate[i]
                            } else {
                                "#"
                            }
                        } else "#/10",
                        isdataofevery.value))
                }
                itemsIndexed(
                    if (isdataget.value)indexCinema else emptyList()
                ) { _, item ->
                    ItemRow(item = item)
                }
            }

        }
    }

     @Suppress("DEPRECATED_IDENTITY_EQUALS")
     fun parsing(): List<Pair<String, String>> {
         if ((checkSelfPermission(Manifest.permission.INTERNET)
                     !== PackageManager.PERMISSION_GRANTED)
         ) {
             requestPermissions(arrayOf(Manifest.permission.INTERNET), 1)
         }
           try {
               val doc: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
               val titlesCinema: Elements = doc.getElementsByAttributeValue("class", "cinemaList_info")
               val nameCinema = mutableListOf<String>()
               val nameAddress = mutableListOf<String>()
               titlesCinema.forEach{
                   nameCinema.add(it.child(0).text())
                   nameAddress.add(it.child(1).text())
               }
               val resCinema: List<Pair<String, String>> = nameCinema.zip(nameAddress)
               return resCinema
           } catch (ex: Exception) {
               Log.e("ErrorOFParsing", ex.toString())
           }

         return emptyList()
     }
    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    suspend fun parsingofcinema():  CinemaData = withContext(Dispatchers.IO) {
        if ((checkSelfPermission(Manifest.permission.INTERNET)
                    !== PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(arrayOf(Manifest.permission.INTERNET), 1)
        }
        try {
            val ratingofcinema = mutableListOf<String>()
            val urlimage = mutableListOf<String>()
            launch {
                val doc: Document = Jsoup.connect("https://omsk.kinoafisha.info/cinema/").get()
                val titlesRating: Elements =
                    doc.getElementsByAttributeValue("class", "cinemaList_ref")
                titlesRating.forEach {
                    val docofCinema = Jsoup.connect(it.attr("href")).get()
                    val titleRate: Elements =
                        docofCinema.getElementsByAttributeValue("class", "rating_inner")
                    val image : Elements = docofCinema.getElementsByAttributeValue("class", "picture_image")
                    urlimage.add(image[0].attr("src"))
                    ratingofcinema.add(titleRate[0].child(0).text())
                }
            }
            return@withContext CinemaData(emptyList(), ratingofcinema, urlimage)

        } catch (ex: Exception) {
            Log.e("ErrorOFParsing", ex.toString())
        }
        return@withContext CinemaData(emptyList(), emptyList(), emptyList())
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
                    }
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