package com.example.cinemas

import android.util.Log
import android.util.LruCache
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemas.data.CinemaData
import com.example.cinemas.data.ItemRowModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("cinema/" +
                "{data}/" +
                "{addr}/" +
                "{phone}/" +
                "{url}/" +
                "{urlcinema}",
            arguments = listOf(
                navArgument("data"){
                    type = NavType.StringType
                    defaultValue = "Name"
                    nullable = true
                },
                navArgument("addr"){
                    type = NavType.StringType
                    defaultValue = "Address"
                    nullable = true
                },
                navArgument("phone"){
                    type = NavType.StringType
                    defaultValue = "88005553535"
                    nullable = true
                },
                navArgument("url"){
                    type = NavType.StringType
                    defaultValue = "uri"
                    nullable = true
                },
                navArgument("urlcinema"){
                    type = NavType.StringType
                    defaultValue = "urna"
                    nullable = true
                }
            )) { entry ->
            CinemaInfo(entry.arguments?.getString("data") ?: "",
                entry.arguments?.getString("addr") ?: "",
                entry.arguments?.getString("phone") ?: "",
                entry.arguments?.getString("url")?.replace(
                    oldValue = "[",
                    newValue = "/"
                ) ?: "",
                entry.arguments?.getString("urlcinema")?.replace(
                    oldValue = "[",
                    newValue = "/"
                ) ?: "",
                navController
                )
        }
        composable("movie/{urlMovie}",
            arguments = listOf(navArgument("urlMovie"){
                type = NavType.StringType
                defaultValue = "oops"
                nullable = true
            })){entry ->
            Movie(entry.arguments?.getString("urlMovie")?.replace(
                oldValue = "[",
                newValue = "/"
            ) ?: "")
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
    MainList(navController)
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
val memoryCache = LruCache<String, ItemRowModel>(1024 * 4)
@Suppress("CAST_NEVER_SUCCEEDS", "DEPRECATED_IDENTITY_EQUALS")
@Composable
fun MainList(navController: NavController) {
    val isdataget = remember {
        mutableStateOf(false)
    }
    val isdataofevery = remember {
        mutableStateOf(false)
    }
    val listR = remember {
        mutableStateListOf<ItemRowModel>()
    }
    var cinemas: List<Pair<String, String>> = listOf()
    var cinemadata = CinemaData(emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

    runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            cinemas =  parsing()
            isdataget.value = true
        }
        CoroutineScope(Dispatchers.IO).launch {
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
            
            if (cinemas.size!= Parsing.listRate.size) {
                val er = runCatching {
                    runBlocking {
                        launch {
                            for (j in 0 until cinemas.size) {
                                Parsing.parsinRate(j, cinemas)
                                listR.add(Parsing.listRate[j])
                            }
                        }
                    }
                }
                er.onFailure {
                    Log.e("Error", it.toString())
                }
            } else Parsing.listRate.forEach { listR.add(it) }

//            for (i in 0 until cinemas.size) {
//                val itemRow = getFromCache(i.toString())
//                if (itemRow != null) {
//                    if (itemRow.dataget) {
//                        itemRow.let {
//                            indexCinema.add(
//                                ItemRowModel(
//                                    itemRow.imageurl,
//                                    itemRow.title,
//                                    itemRow.city,
//                                    itemRow.rate,
//                                    itemRow.dataget,
//                                    itemRow.phone,
//                                    itemRow.cinemaUrl
//                                )
//                            )
//                        }
//                    }
//                    } else {
//                        indexCinema.add(
//                            ItemRowModel(
//                                if (isdataofevery.value)
//                                    cinemadata.urlimage[i] else "",
//                                cinemas[i].first,
//                                cinemas[i].second,
//                                if (isdataofevery.value) {
//                                    if (cinemadata.rate[i].lowercase() != "мало голосов") {
//                                        cinemadata.rate[i]
//                                    } else {
//                                        "#"
//                                    }
//                                } else "#/10",
//                                isdataofevery.value,
//                                if (isdataofevery.value) cinemadata.phonenumber[i] else "",
//                                if (isdataofevery.value) cinemadata.urlcinema[i] else ""
//                            )
//                        )
//                    if (isdataofevery.value) saveToCache(i.toString(),
//                        ItemRowModel(
//                                cinemadata.urlimage[i] ,
//                            cinemas[i].first,
//                            cinemas[i].second,
//                            if (cinemadata.rate[i].lowercase() != "мало голосов") {
//                                    cinemadata.rate[i]
//                                } else {
//                                    "#"
//                                }
//                            ,
//                            isdataofevery.value,
//                            cinemadata.phonenumber[i],
//                            cinemadata.urlcinema[i]
//                        )
//                        )
//
//                    }
//
//            }

            itemsIndexed(
                if (isdataget.value) listR else emptyList()
            ) { _, item ->
                ItemRow(item = item, navController)
            }
        }
    }
}
fun saveToCache(key: String, itemRowModel: ItemRowModel) {
    memoryCache.put(key, itemRowModel)
}

fun getFromCache(key: String): ItemRowModel? {
    return memoryCache.get(key)
}
