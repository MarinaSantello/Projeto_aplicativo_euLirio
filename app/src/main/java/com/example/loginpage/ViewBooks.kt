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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.shortStory.CallShortStory
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Anuncios
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.components.TabsUserStories
import com.example.loginpage.ui.theme.*
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//class ViewBooks : ComponentActivity() {
//
//    //    @OptIn(ExperimentalPagerApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    ShowBooks(0, 40.dp, 1, navController)
//                }
//            }
//        }
//    }
//}

//@ExperimentalPagerApi
@Composable
fun ShowBooks(
    userID: Int,
    bottomBarLength: Dp,
    type: Int,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFAF0B8)),
    ) {
        //Layout do perfil
        when (type) {
            //Layout do feed
            1 -> TabsFeed(userID, bottomBarLength, navController)

            //Layout de pesquisa
            2 -> TabsFeedSearch(userID, bottomBarLength, navController)

            //Layout do carrinho
            3 -> ShowItemsCart(userID, bottomBarLength, navController)

            //Layout das obras do usuario
            4 -> TabsUserStories(userID, bottomBarLength, navController)

            //Layout das publicacoes favoritas
            5 -> TabsUserPubFavoritas(userID, bottomBarLength, navController)

            //Layout das publicacoes lidas
            6 -> TabsUserPubLidas(userID, bottomBarLength, navController)
        }
    }


}

@OptIn(ExperimentalUnitApi::class)
//@ExperimentalPagerApi
@Composable
fun TabsFeed(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {
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
                        AnnouncementCard(it, userID, navController, 1, true, true)
                    }
                }
//                Text(text = "api deu b.o.")
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
                        ShortStorysCard(it, navController, userID, true, true)
                    }
                }
            }
            2 -> Text(text = "Pequenas Histórias")
        }
    }
}



