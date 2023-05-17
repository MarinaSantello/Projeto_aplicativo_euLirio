package com.example.loginpage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.LikeAnnouncement
import com.example.loginpage.models.Recommendation
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor

class RecommendationPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowRecommendation(rememberNavController(), 6)
                }
            }
        }
    }
}

@Composable
fun ShowRecommendation(
    navController: NavController,
    idRecommendation: Int
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }
    var userAuthor = remember { mutableStateOf(false) }

    val userID = UserIDrepository(context).getAll()[0].idUser

    var recommendation by remember {
        mutableStateOf<Recommendation?>(null)
    }

    var idAnnouncement = 0

    CallRecommendationAPI.getRecommendationByID(idRecommendation, userID) {
        recommendation = it

        idAnnouncement = it.anuncioID
    }

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(idAnnouncement, userID) {
        announcement = it

        userAuthor.value = (it.usuario[0].idUsuario == userID)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RECOMENDAÇÃO",
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
                                .padding(end = 16.dp)
                                .fillMaxHeight()
                                .width(32.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .clickable { },
                            tint = colorResource(id = R.color.eulirio_black)
                        )

                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
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
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
                elevation = 0.dp
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 750.dp)
                    .width(50.dp),
                backgroundColor = colorResource(id = R.color.eulirio_beige_color_background)
            ) {
                Row(){
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = "favorita",
                        modifier = Modifier
                            .padding(start = 110.dp, top = 8.dp)
                            .size(20.dp),
                        tint = colorResource(id = R.color.eulirio_red)
                    )
                    Text(text = "570",
                        Modifier.padding(top = 8.dp),
                        fontFamily = QuickSand, fontSize = 16.sp)

                    Spacer(modifier = Modifier.padding(50.dp))

                    Icon(
                        Icons.Outlined.Bookmark,
                        contentDescription = "save",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(20.dp),
                        tint = colorResource(id = R.color.eulirio_yellow_card_background)
                    )
                    Text(text = "182",
                        Modifier.padding(top = 8.dp),
                        fontFamily = QuickSand, fontSize = 16.sp)
                }
            }
        }
    ) {
        if (recommendation != null && announcement != null) RecommendationView(
            recommendation!!,
            announcement!!,
            navController,
            it.calculateBottomPadding()
        )
    }
}

@Composable
fun RecommendationView(
    recommendation: Recommendation,
    announcement: AnnouncementGet,
    navController: NavController,
    bottomBarLength: Dp
) {
    val filledStars = floor(announcement.avaliacao).toInt()
    val unfilledStars = (5 - ceil(announcement.avaliacao)).toInt()
    val halfStar = !(announcement.avaliacao.rem(1).equals(0.0))

    val mouths = listOf(
        "Jan.",
        "Fev.",
        "Mar.",
        "Abr.",
        "Mai.",
        "Jun.",
        "Jul.",
        "Ago.",
        "Set.",
        "Out.",
        "Nov.",
        "Dez."
    )
    val date = recommendation.dataHora.toString().split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]

    Column() {

        //TopBar no lugar do card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp),
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
            shape = RoundedCornerShape(12.dp)
        ) {
            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Column() {
                Text(
                    text = announcement.titulo,
                    fontFamily = SpartanBold,
                    modifier = Modifier.padding(top = 60.dp, start = 130.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Escrito por ${announcement.usuario[0].userName}",
                    fontFamily = Spartan,
                    modifier = Modifier.padding(start = 130.dp),
                    textAlign = TextAlign.Center
                )

                val generos = announcement.generos

                LazyRow() {
                    items(generos) {
                        Card(
                            modifier = Modifier
                                .height(10.dp)
                                .padding(end = 4.dp)
                            ,
                            backgroundColor = colorResource(R.color.eulirio_purple_text_color_border),
                            shape = RoundedCornerShape(100.dp),
                        ) {
                            Text(
                                text = it.nome,
                                fontSize = 7.sp,
                                fontFamily = MontSerratSemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(12.dp, 1.dp),
                                color = Color.White
                            )

                        }
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxSize()
            ){

                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }

                if (halfStar) {
                    Icon(
                        imageVector = Icons.Outlined.StarHalf,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }

                repeat(unfilledStars) {
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = colorResource(R.color.eulirio_purple_text_color_border)
                    )
                }
            }
        }

    }

    Spacer(modifier = Modifier.padding(vertical = 30.dp))

    Column(
        Modifier.height(1000.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                Modifier.clickable {
                    navController.navigate("${Routes.User.name}/${announcement.usuario[0].idUsuario}")
                }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(announcement.usuario[0].foto),
                    contentDescription = "foto da pessoa",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .size(40.dp)
                        .clip(
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop,


                    )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = announcement.usuario[0].nomeUsuario,
                        fontSize = 15.sp,
                        fontFamily = SpartanMedium,
                        modifier = Modifier.padding(top = 11.dp)
                    )
                    Text(
                        text = "@${announcement.usuario[0].userName}",
                        fontSize = 12.sp,
                        fontFamily = Spartan
                    )
                }
            }

            Text(text = "${date[2]} $mouth ${date[0]}",
                modifier = Modifier.padding(start = 160.dp),
                fontFamily = SpartanExtraLight,
                textAlign = TextAlign.End,
                fontSize = 14.sp)

            Spacer(modifier = Modifier.padding(vertical = 12.dp) )

            //sinopse
            Text(text = recommendation.conteudo,
                fontFamily = QuickSand,
                fontSize = 12.sp

            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview3() {
//    LoginPageTheme {
//        ShowRecommendation()
//    }
//}