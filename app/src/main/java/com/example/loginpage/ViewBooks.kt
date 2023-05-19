package com.example.loginpage

import android.os.Bundle
import android.provider.MediaStore.Audio.Genres
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.API.shortStory.CallShortStory
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.models.*
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.components.TabsUserStories
import com.example.loginpage.ui.components.generateRecommendationCard
import com.example.loginpage.ui.theme.*
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.math.ceil
import kotlin.math.floor

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
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>,
    navController: NavController,
    tabIndex: MutableState<Int>?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFAF0B8)),
    ) {
        //Layout do perfil
        when (type) {
            //Layout do feed
            1 -> TabsFeed(userID, bottomBarLength, topBarState, bottomBarState, navController)

            //Layout de pesquisa
            2 -> TabsFeedSearch(userID, topBarState, bottomBarLength, navController, tabIndex!!)

            //Layout do carrinho
            3 -> ShowItemsCart(userID, bottomBarLength, navController)

            //Layout das obras do usuario
            4 -> TabsUserStories(userID, bottomBarLength, navController)

            //Layout das publicacoes favoritas
            5 -> TabsUserPubFavoritas(userID, bottomBarLength, navController)

            //Layout das publicacoes lidas
            6 -> TabsUserPubLidas(userID, bottomBarLength, navController)

            //Layout dos livros comprados
            7 -> PurchasedAnnouncementsView(userID, bottomBarLength, navController)
        }
    }


}

@OptIn(ExperimentalUnitApi::class)
//@ExperimentalPagerApi
@Composable
fun TabsFeed(
    userID: Int,
    bottomBarLength: Dp,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>,
    navController: NavController
) {
    val context = LocalContext.current
    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    var scroll by remember { mutableStateOf(0) }

    val tabs = listOf("Livros", "Pequenas Histórias", "Recomendações")

    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        if (scrollState.firstVisibleItemIndex > scroll) {
            topBarState.value = false
            bottomBarState.value = false
//            fabState.value = false
            scroll = scrollState.firstVisibleItemIndex
        } else if (scrollState.firstVisibleItemIndex < scroll) {
            topBarState.value = true
            bottomBarState.value = true
//            fabState.value = true
            scroll = scrollState.firstVisibleItemIndex
        }
    }

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
                var announcementIsNull by remember {
                    mutableStateOf(false)
                }

                //CallAnnouncementAPI.getAnnouncements {
                CallAnnouncementAPI.getAllAnnouncementsByGenresUser(userID) {
                    if (it.isNullOrEmpty()) announcementIsNull = true
                    else announcements = it
                }

                if (announcementIsNull) Text(text = "Você não possui livros no seu feed por enquanto.")

                else LazyColumn(
                    state = scrollState,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(announcements) {
                        AnnouncementCard(it, userID, navController, 1, true, true)
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

                LazyColumn(
                    state = scrollState,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(shortStory) {
                        ShortStorysCard(it, navController, userID, true, true)
                    }
                }
            }
            2 -> {
                var recomendations by remember {
                    mutableStateOf(listOf<Recommendation>())
                }

                var recomendationIsNull by remember {
                    mutableStateOf(false)
                }

                CallRecommendationAPI.getRecommendationByUserId(userID){
                    if(it.isNullOrEmpty()) recomendationIsNull = true
                    else recomendations = it
                }

                if (recomendationIsNull) Text(text = "Você não possui recomendações no seu feed por enquanto.")

                LazyColumn(
                    state = scrollState,
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(recomendations) {
                        generateRecommendationCard(it, navController, userID)
                    }
                }

            }
        }
    }
}





