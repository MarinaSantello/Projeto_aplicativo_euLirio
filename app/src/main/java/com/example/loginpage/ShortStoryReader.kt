package com.example.loginpage

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.loginpage.ui.theme.*


class ShortStoryReader : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenBuilder()
                }
            }
        }
    }
}

var offset: MutableState<Float> = mutableStateOf(0f)
var visibility: MutableState<Boolean> = mutableStateOf(true)

@Composable
fun ScreenBuilder() {
    val scaffoldState = rememberScaffoldState()

    val html =
        "<p><span style=\"font-size: 36pt;\">Tamanho de Fonte (de 8 pt a 36 pt) - Exemplo com 36 Pt</span></p>\n" +
                "\n" +
                "<p><strong>Negrito</strong> | <em>Italico </em> | <span style=\"text-decoration: underline;\">Underline</span></p>\n" +
                "\n" +
                "<p>Esquerda</p>\n" +
                "<p style=\"text-align: center;\">Centro</p>\n" +
                "<p style=\"text-align: right;\">Direita kn cfkjn kfjn kjn knk vnfkjgnfd sjkdfcjsdjfcv sdjfh jdhdjf bds fvghjdsdf gvjhsdfjvhsdfjvch vjvj dfjvjdf vjdfjngikejn ifn d ldsfvdsl.</p>\n" +
                "<p style=\"text-align: justify;\">Justificado jhsbdfcjs dhb dsbfjhgfbcsghefv fbhghwe fvfdsghwef vwfdc ebghvfdesw wsdefgc wwedsfvgws </p>\n" +
                "<p style=\"padding-left: 120px; text-align: center;\"><span style=\"text-decoration: underline; font-size: 18pt;\"><em><strong>Exemplo de Tudo Junto</strong></em></span></p>" +
                "<p style=\"text-align: justify; padding-left: 40px;\">Identado</p>"

    val htmlFontStyle =
        "<head><link href=\"https://fonts.googleapis.com/css2?family=Noto+Serif&display=swap\" rel=\"stylesheet\"></head>\n" +
                "<body>\n" +
                html +
                "<style>body{font-family: 'Noto Serif', serif;}</style>\n" +
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
            TopBar()
        },
        bottomBar = {
            BottomBar()
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
fun TopBar() {
    AnimatedVisibility(
        visible = visibility.value,
        modifier = Modifier.zIndex(2f),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "HISTÓRIA PEQUENA",
                            fontWeight = FontWeight(700),
                            fontFamily = MontSerratBold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                /* TODO */
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                null,
                                tint = colorResource(R.color.eulirio_black)
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
                                text = "Lorem Ipsum",
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
                                    text = "n.sebastian",
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
fun BottomBar() {
    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember {
        mutableStateOf(false)
    }

    var viewState by remember {
        mutableStateOf(false)
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
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable { likeState = !likeState }
                            ) {
                                //Linha de curtir
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable { likeState = !likeState }
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
                                        text = "570",
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
                                        .clickable { saveState = !saveState }
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
                                        text = "570",
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
                                        .clickable { viewState = !viewState }
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
                                        text = "570",
                                        fontSize = 14.sp,
                                        fontFamily = Montserrat2,
                                        fontWeight = FontWeight.W500,
                                        color = colorResource(R.color.eulirio_black)

                                    )
                                    Text(text = offset.value.toString())
                                }
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
                .size(200.dp),
            factory = { context ->
                val webView = WebView(context)

                webView.apply {
                    loadData(htmlCode, "text/html", "utf-8")
                }

                webView.settings.javaScriptEnabled = true
                webView.setOnTouchListener { view, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        visibility.value = !visibility.value
                    }
                    // Forward touch events to parent view
                    view.parent?.requestDisallowInterceptTouchEvent(false)
                    false
                }
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
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    LoginPageTheme {
        ScreenBuilder()
    }
}