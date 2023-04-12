package com.example.loginpage

import android.os.Bundle
import android.provider.MediaStore.Audio.Genres
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStory
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.theme.*
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class ViewBooks : ComponentActivity() {
//    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowBooks(0, 40.dp, 1)
                }
            }
        }
    }
}

//@ExperimentalPagerApi
@Composable
fun ShowBooks(userID: Int, bottomBarLength: Dp, type: Int) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFAF0B8)),

    ) {
        //Layout do perfil
        when (type) {
            1 -> TabsFeed(userID, bottomBarLength)

            //Layout do feed
            2 -> TabsFeed(userID, bottomBarLength)

            //Layout das obras do usuario
            3 -> TabsUserStories(userID, bottomBarLength)
        }
    }


}

@OptIn(ExperimentalUnitApi::class)
//@ExperimentalPagerApi
@Composable
fun TabsFeed(userID: Int, bottomBarLength: Dp) {
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabs = listOf("Livros", "Pequenas Histórias", "Recomendações")


    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
//            selectedTabIndex = tabIndex.currentPage,
            selectedTabIndex = tabIndex,
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
            modifier = Modifier.height(40.dp),
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        val icons = listOf(Icons.Outlined.MenuBook, Icons.Outlined.FormatAlignCenter, Icons.Outlined.LocalLibrary)

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                icons[index],
                                contentDescription = "icone de livro",
                                modifier = Modifier
                                    .height(16.dp)
                                    .padding(end = 8.dp),
                            )
                            Text(
                                title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    selectedContentColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unselectedContentColor = Color.Black,
//                    selected = tabIndex.currentPage == index,
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
//                    onClick = {
//                        coroutineScope.launch {
//                            tabIndex.animateScrollToPage(index)
//                        }
//                    },
                )
            }
        }
//        when (tabIndex.currentPage) {
        when (tabIndex) {
            0 -> {
                var announcements by remember {
                    mutableStateOf(listOf<AnnouncementGet>())
                }

                //CallAnnouncementAPI.getAnnouncements {
                CallAnnouncementAPI.getAllAnnouncementsByGenresUser(userID) {
                    announcements = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(announcements) {
                        AnnouncementCard(it)
                    }
                }
            }
            1 -> {
                var shortStory by remember {
                    mutableStateOf(listOf<ShortStoryGet>())
                }

                //CallShortStoryAPI.getShortStories {
                CallShortStoryAPI.getShortStoriesByGenreUser(userID) {
                    shortStory = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(shortStory) {
                        ShortStorysCard(it)
                    }
                }
            }
            2 -> Text(text = "Pequenas Histórias")
        }
    }
}

@Composable
fun TabsUserStories(userID: Int, bottomBarLength: Dp) {
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(0)
    }

    val tabs = listOf("Publicadas", "Desativadas")


    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
//            selectedTabIndex = tabIndex.currentPage,
            selectedTabIndex = tabIndex,
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
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
                    selectedContentColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unselectedContentColor = Color.Black,
//                    selected = tabIndex.currentPage == index,
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
//                    onClick = {
//                        coroutineScope.launch {
//                            tabIndex.animateScrollToPage(index)
//                        }
//                    },
                )
            }
        }
//        when (tabIndex.currentPage) {
        when (tabIndex) {
            0 -> {

                val items = listOf("Livros", "Pequenas Histórias")

                Box (
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Card(modifier = Modifier
                        .height(36.dp)
                        .clickable { expanded = true }
                        .fillMaxWidth(.6f)
                        .padding(end = 12.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_grey_background),
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) {


                        Row(
                            modifier = Modifier
                                .padding(start = 13.dp, end = 13.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = items[selectedItem],
                                fontSize = 10.sp,
                            )
                            Icon(
                                Icons.Rounded.ExpandMore,
                                contentDescription = "Mostrar mais",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                        }


                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(onClick = {
                                selectedItem = index
                                expanded = false
                            }) {
                                Text(text = item)
                            }
                        }
                    }
                }

                var announcements by remember {
                    mutableStateOf(listOf<AnnouncementGet>())
                }

                //CallAnnouncementAPI.getAnnouncements {
                CallAnnouncementAPI.getAllAnnouncementsByGenresUser(userID) {
                    announcements = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(announcements) {
                        AnnouncementCard(it)
                    }
                }

            }
            1 -> {
                var shortStory by remember {
                    mutableStateOf(listOf<ShortStoryGet>())
                }

                //CallShortStoryAPI.getShortStories {
                CallShortStoryAPI.getShortStoriesByGenreUser(userID) {
                    shortStory = it
                }

                LazyColumn(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                    items(shortStory) {
                        ShortStorysCard(it)
                    }
                }
            }
        }
    }
}
