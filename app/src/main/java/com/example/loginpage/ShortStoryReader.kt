package com.example.loginpage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.FavoriteShortStorie
import com.example.loginpage.models.LikeShortStorie
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.models.VisualizationShortStorie
import com.example.loginpage.ui.theme.*
import kotlinx.coroutines.launch

//class ShortStoryReader : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    ScreenBuilder()
//                }
//            }
//        }
//    }
//}

var offset: MutableState<Float> = mutableStateOf(0f)
var visibility: MutableState<Boolean> = mutableStateOf(true)

@Composable
fun ScreenBuilder(
    shortStory: ShortStoryGet,
    navController: NavController
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val htmlFontStyle =
        "<head><link href=\"https://fonts.googleapis.com/css2?family=Noto+Serif&display=swap\" rel=\"stylesheet\"></head>\n" +
                "<body>\n" +
                shortStory.historia +
                "<style>body{font-family: 'Noto Serif', serif; margin: 12px;}</style>\n" +
                "</body>"
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        visibility.value = !visibility.value
                    }
                )
            },
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(shortStory, context, navController)
        },
        bottomBar = {
            BottomBar(shortStory)
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            WebViewComponent(htmlCode = htmlFontStyle) {
                Log.i("teste android vivew", it.toString())
//            visibility.value = it
            }
        }
    }
}
@Composable
fun PageRender(htmlText: String) {
    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember {
        mutableStateOf(false)
    }

    var viewState by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
//            .zIndex(0f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        visibility.value = !visibility.value
                    }
                )
            },
    ) {
    }

}

@Composable
fun TopBar(
    shortStory: ShortStoryGet,
    context: Context,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = visibility.value,
        modifier = Modifier.zIndex(2f),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.title_short_story).uppercase(),
                                modifier = Modifier
                                    .fillMaxWidth(.82f),
//                                .padding(end = 44.dp),
                                color = colorResource(id = R.color.eulirio_black),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h2,
                                fontSize = 20.sp
                            )

                            Icon(
                                Icons.Rounded.MoreVert,
                                contentDescription = "botao de menu",
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 20.dp)
                                    .fillMaxSize(.9f)
                                    .clip(RoundedCornerShape(100.dp))
                                    .clickable { },
                                tint = colorResource(id = R.color.eulirio_black)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Rounded.ChevronLeft,
                                contentDescription = "botao para voltar",
                                modifier = Modifier
                                    .fillMaxSize(.8f)
                                    .padding(start = 12.dp)
                                    .clip(RoundedCornerShape(100.dp)),
                                tint = colorResource(id = R.color.eulirio_black)
                            )
                        }
                    },
                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                    elevation = 0.dp
                )

                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
////                        .zIndex(1f)
//                        .pointerInput(Unit) {
//                            detectTapGestures(
//                                onDoubleTap = {
//                                    visibility.value = !visibility.value
//                                }
//                            )
//                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                        elevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = shortStory.titulo,
                                fontWeight = FontWeight(700),
                                fontFamily = SpartanBold,
                                fontSize = 25.sp
                            )
                            Row() {
                                Text(
                                    text = "Escrito por",
                                    fontWeight = FontWeight(300),
                                    fontFamily = SpartanExtraLight,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = shortStory.usuario[0].userName,
                                    fontWeight = FontWeight(600),
                                    fontFamily = SpartanBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BottomBar(shortStory: ShortStoryGet) {


    var likeState by remember {
        mutableStateOf(shortStory.curtido)
    }

    var quantidadeLikesState by remember {
        mutableStateOf("")
    }


    var saveState by remember {
        mutableStateOf(shortStory.favorito)
    }

    var quantidadeFavoritosState by remember{
        mutableStateOf("")
    }


    var viewState by remember {
        mutableStateOf(shortStory.lido)
    }

    var quantidadeViewsState by remember{
        mutableStateOf("")
    }


    CallLikeAPI.countShortStoriesLikes(13){
        quantidadeLikesState = it.qtdeCurtidas
    }

    CallFavoriteAPI.countFavoritesShortStories(13){
        quantidadeFavoritosState = it.qtdeFavoritos
    }

    CallVisualizationAPI.countViewShortStorie(13){
        quantidadeViewsState = it.qtdeLidos
    }


    AnimatedVisibility(
        visible = visibility.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar(
                backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                elevation = 0.dp,
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 12.dp)
                                .clickable { likeState = !likeState }
                        ) {
                            //Linha de curtir
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {

                                        likeState = !likeState
                                        if (likeState) {

                                            var shortStorieLike = LikeShortStorie(
                                                idHistoriaCurta = 13,
                                                idUsuario = 113
                                            )
                                            CallLikeAPI.likeShortStorie(shortStorieLike)

                                        } else {
                                            CallLikeAPI.countShortStoriesLikes(13) {
                                                quantidadeLikesState = it.qtdeCurtidas
                                            }


                                            var shortStorieUnLike = LikeShortStorie(
                                                idHistoriaCurta = 13,
                                                idUsuario = 113
                                            )
                                            CallLikeAPI.dislikeShortStorie(shortStorieUnLike)
                                        }
                                    }
                            ) {

                                //Verificação se o usuário curtiu a publicação
                                if (likeState) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        tint = colorResource(R.color.eulirio_black)
                                    )
                                } else Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "icone de curtir",
                                    modifier = Modifier,
                                    tint = colorResource(R.color.eulirio_black)
                                )

                                Spacer(modifier = Modifier.width(2.dp))

                                Text(
                                    text = quantidadeLikesState,
                                    fontSize = 14.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.W500,
                                    color = colorResource(R.color.eulirio_black)
                                )
                            }

                            //Linha de favoritar
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {
                                        saveState = !saveState

                                        CallFavoriteAPI.countFavoritesShortStories(13) {
                                            quantidadeFavoritosState = it.qtdeFavoritos
                                        }

                                        if (!saveState) {
                                            val favoriteShortStorieUnCheck =
                                                FavoriteShortStorie(
                                                    idHistoriaCurta = 13,
                                                    idUsuario = 113
                                                )
                                            CallFavoriteAPI.unfavoriteShortStorie(
                                                favoriteShortStorieUnCheck
                                            )
                                        } else {
                                            val favoriteShortStorieCheck = FavoriteShortStorie(
                                                idHistoriaCurta = 13,
                                                idUsuario = 113
                                            )
                                            CallFavoriteAPI.favoriteShortStorie(
                                                favoriteShortStorieCheck
                                            )
                                        }
                                    }
                            ) {

                                //Verificação se o favoritou a publicação
                                if (saveState) {
                                    Icon(
                                        Icons.Outlined.Bookmark,
                                        contentDescription = "icone de favoritar",
                                        tint = colorResource(R.color.eulirio_black)
                                    )
                                } else Icon(
                                    Icons.Outlined.BookmarkAdd,
                                    contentDescription = "icone de favoritar",
                                    tint = colorResource(R.color.eulirio_black)
                                )

                                Spacer(modifier = Modifier.width(2.dp))

                                Text(
                                    text = quantidadeFavoritosState  ?: "0",
                                    fontSize = 14.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.W500,
                                    color = colorResource(R.color.eulirio_black)
                                )
                            }

                            //Linha de visualização
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {

                                        viewState = !viewState
                                        if (!viewState) {
                                            val unViewShortStorie = VisualizationShortStorie(
                                                idHistoriaCurta = 13,
                                                idUsuario = 113
                                            )
                                            CallVisualizationAPI.unViewShortStorie(unViewShortStorie)

                                            CallVisualizationAPI.countViewShortStorie(13){
                                                quantidadeViewsState = it.qtdeLidos
                                            }
                                        } else {
                                            val viewShortStorie = VisualizationShortStorie(
                                                idHistoriaCurta = 13,
                                                idUsuario = 113
                                            )
                                            CallVisualizationAPI.viewShortStorie(viewShortStorie)

                                            CallVisualizationAPI.countViewShortStorie(13){
                                                quantidadeViewsState = it.qtdeLidos
                                            }
                                        }




                                    }
                            ) {

                                //Verificação se o usuário visualizou a publicação
                                if (viewState) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de visualizacão",
                                        tint = colorResource(R.color.eulirio_black)
                                    )
                                } else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de visualizacão",
                                    tint = colorResource(R.color.eulirio_black)
                                )

                                Spacer(modifier = Modifier.width(2.dp))

                                Text(
                                    text = quantidadeViewsState ?: "0",
                                    fontSize = 14.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.W500,
                                    color = colorResource(R.color.eulirio_black)

                                )
//                                    Text(text = offset.value.toString())
                            }
                        }
                    }
                }
            )
        }
    )
}

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WebViewComponent(htmlCode: String, onTap: (Boolean) -> Unit) {
    var tap by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val webView = WebView(context)

                webView.apply {
                    loadData(htmlCode, "text/html", "utf-8")
                }

//                webView.settings.javaScriptEnabled = true
//                webView.setOnTouchListener { view, event ->
//                    if (event.action == MotionEvent.ACTION_DOWN) {
//                        visibility.value = !visibility.value
//                    }
//                    // Forward touch events to parent view
//                    view.parent?.requestDisallowInterceptTouchEvent(false)
//                    false
//                }
                webView
            }
        )
    }
//
//    AndroidView(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp, 0.dp)
////            .onGloballyPositioned {
////                offset.value += it.positionInParent().y
////            }
//            .pointerInteropFilter { true },
////            .pointerInput(Unit) {
////                detectTapGestures(
////                    onTap = {
////                    }
////                )
////            },
//        factory = { context ->
//            WebView(context).apply {
//                loadData(htmlCode, "text/html", "utf-8")
//            }
//        }
//    )

    if (visibility.value) Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        visibility.value = !visibility.value
                    }
                )
            }
    )
    
    else Box (
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            Icons.Rounded.Info,
            contentDescription = "icone para vizualizar mais informações sobre a publicação",
            modifier = Modifier
                .padding(top = 12.dp, end = 12.dp)
                .clickable { visibility.value = true },
            tint = colorResource(id = R.color.eulirio_purple_text_color_border)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview7() {
//    LoginPageTheme {
//        ScreenBuilder()
//    }
//}