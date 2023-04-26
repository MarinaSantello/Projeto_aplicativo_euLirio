package com.example.loginpage.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Anuncios
import com.example.loginpage.models.ShortStoryGet

@Composable
fun TabsUserStories(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember {
        mutableStateOf(false)
    }

    val tabs = listOf("Publicadas", "Desativadas")

    val items = listOf("Livros", "Pequenas Histórias")

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
//            selectedTabIndex = tabIndex.currentPage,
            selectedTabIndex = tabIndex,
            backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_beige_color_background),
            modifier = Modifier.height(40.dp),
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        val icons = listOf(Icons.Outlined.MenuBook, Icons.Outlined.FormatAlignCenter)

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Icon(
//                                icons[index],
//                                contentDescription = "icone de livro",
//                                modifier = Modifier
//                                    .height(16.dp)
//                                    .padding(end = 8.dp),
//                            )
                            Text(
                                title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    selectedContentColor = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border),
                    unselectedContentColor = Color.Black,
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }

        when (tabIndex) {
            0 -> {
                var selectedItem by remember {
                    mutableStateOf(0)
                }

                Box (
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 4.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Card(modifier = Modifier
                        .height(36.dp)
                        .clickable { expanded = true }
                        .fillMaxWidth(.6f),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = items[selectedItem],
                                fontSize = 12.sp,
                            )
                            Icon(
                                Icons.Rounded.ExpandMore,
                                contentDescription = "Mostrar mais",
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(.56f)
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = index

                                    expanded = false
                                }
                            ) {
                                Text(text = item)
                            }
                        }
                    }
                }

                if (selectedItem == 0) {

                    var announcementSize by remember {
                        mutableStateOf(0)
                    }
                    var announcementsUser by remember {
                        mutableStateOf(listOf<Anuncios>())
                    }
                    var announcementIsNull by remember {
                        mutableStateOf(false)
                    }

                    CallAPI.getUser(userID.toLong()) {
                        if (it.anunciosActivate.isNullOrEmpty()) announcementIsNull = true
                        else {
                            announcementSize = (it.anunciosActivate!!.size - 1) ?: 0
                            announcementsUser = it.anunciosActivate!!
                        }
                    }

                    var announcement by remember {
                        mutableStateOf(listOf<AnnouncementGet>())
                    }

                    if (announcementsUser.isNotEmpty()) Column() {
                        for (i in 0..announcementSize) {
                            Log.i("id anuncio $i", announcementsUser[i].id.toString())

                            CallAnnouncementAPI.getAnnouncementsByUser(1, userID) {
                                announcement = it
                            }
//
                            if (announcement.isNotEmpty()) AnnouncementCard(announcement[i], userID, navController, 2, true, true)
                        }
                    }

                    if (announcementIsNull) Text(text = "Você não possui livros publicados.")

                }
                else if (selectedItem == 1) {

                    var shortStoriesSize by remember {
                        mutableStateOf(0)
                    }
                    var shortStoriesUser by remember {
                        mutableStateOf(listOf<Anuncios>())
                    }
                    var shortStoriesIsNull by remember {
                        mutableStateOf(false)
                    }

                    CallAPI.getUser(userID.toLong()) {
                        if (it.shortStoriesActivate.isNullOrEmpty()) shortStoriesIsNull = true
                        else {
                            shortStoriesSize = (it.shortStoriesActivate!!.size - 1) ?: 0
                            shortStoriesUser = it.shortStoriesActivate!!
                        }
                    }

                    var shortStories by remember {
                        mutableStateOf(listOf<ShortStoryGet>())
                    }

                    if (shortStoriesUser.isNotEmpty()) Column() {
                        for (i in 0..shortStoriesSize) {
                            Log.i("id anuncio $i", shortStoriesUser[i].id.toString())

                            CallShortStoryAPI.getShortStoriesByUser(1, userID) {
                                shortStories = it
                            }

                            if (shortStories.isNotEmpty()) ShortStorysCard(shortStories[i], navController, userID, true, true)
                        }
                    }

                    if (shortStoriesIsNull) Text(text = "Você não possui pequenas histórias publicados.")

                }
            }
            1 -> {
                var selectedItem by remember {
                    mutableStateOf(0)
                }

                Box (
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 4.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Card(modifier = Modifier
                        .height(36.dp)
                        .clickable { expanded = true }
                        .fillMaxWidth(.6f),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = items[selectedItem],
                                fontSize = 12.sp,
                            )
                            Icon(
                                Icons.Rounded.ExpandMore,
                                contentDescription = "Mostrar mais",
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_black)
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(.56f)
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = index

                                    expanded = false
                                }
                            ) {
                                Text(text = item)
                            }
                        }
                    }
                }

                var announcementSize by remember {
                    mutableStateOf(0)
                }
                var announcementsUser by remember {
                    mutableStateOf(listOf<Anuncios>())
                }
                var announcementIsNull by remember {
                    mutableStateOf(false)
                }

                if (selectedItem == 0) {
                    CallAPI.getUser(userID.toLong()) {
                        if (it.anunciosDeactivate.isNullOrEmpty()) announcementIsNull = true
                        else {
                            announcementSize = (it.anunciosDeactivate!!.size - 1) ?: 0
                            announcementsUser = it.anunciosDeactivate!!
                        }
                    }
                    var announcement by remember {
                        mutableStateOf(listOf<AnnouncementGet>())
                    }

                    if (announcementsUser.isNotEmpty()) Column() {
                        for (i in 0..announcementSize) {
                            Log.i("id anuncio $i", announcementsUser[i].id.toString())

                            CallAnnouncementAPI.getAnnouncementsByUser(0, userID) {
                                announcement = it
                            }

                            if (announcement.isNotEmpty()) AnnouncementCard(
                                announcement[i],
                                userID,
                                navController,
                                2,
                                true, true
                            )
                        }
                    }

                    if (announcementIsNull) Text(text = "Você não possui livros desativados.")
                }
                else if (selectedItem == 1) {


                    var shortStoriesSize by remember {
                        mutableStateOf(0)
                    }
                    var shortStoriesUser by remember {
                        mutableStateOf(listOf<Anuncios>())
                    }
                    var shortStoriesIsNull by remember {
                        mutableStateOf(false)
                    }

                    CallAPI.getUser(userID.toLong()) {
                        if (it.shortStoriesDeactivate.isNullOrEmpty()) shortStoriesIsNull = true
                        else {
                            shortStoriesSize = (it.shortStoriesDeactivate!!.size - 1) ?: 0
                            shortStoriesUser = it.shortStoriesDeactivate!!
                        }
                    }

                    var shortStories by remember {
                        mutableStateOf(listOf<ShortStoryGet>())
                    }

                    if (shortStoriesUser.isNotEmpty()) Column() {
                        for (i in 0..shortStoriesSize) {
                            Log.i("id anuncio $i", shortStoriesUser[i].id.toString())

                            CallShortStoryAPI.getShortStoriesByUser(0, userID) {
                                shortStories = it
                            }

                            if (shortStories.isNotEmpty()) ShortStorysCard(shortStories[i], navController, userID, true, true)
                        }
                    }

                    if (shortStoriesIsNull) Text(text = "Você não possui pequenas histórias desativadas.")

                }
            }
        }
    }
}
