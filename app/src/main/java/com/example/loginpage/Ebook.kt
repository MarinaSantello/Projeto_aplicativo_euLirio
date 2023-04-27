package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.cart.CallCartAPI
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.ui.theme.*
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//class Ebook : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    EbookView(1, rememberNavController())
//                }
//            }
//        }
//    }
//}

@Composable
fun EbookView(
    idAnnouncement: Int,
    navController: NavController
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }
    var userAuthor = remember { mutableStateOf(false) }

    val userID = UserIDrepository(context).getAll()[0].idUser

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(idAnnouncement, userID) {
        announcement = it

        userAuthor.value = (it.usuario[0].idUsuario == userID)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!fabState.value)
                            fabState.value = !fabState.value
                    }
                )
            },
        scaffoldState = scaffoldState,
        topBar = {
            TopBarEbook(
                stringResource(R.string.title_ebook),
                topBarState,
                context,
                userAuthor.value,
                navController
            )
        },
        bottomBar = {
            BottomBarEbook(
                bottomBarState,
                userAuthor.value,
                context,
                navController,
                idAnnouncement,
                userID
            )
        },
    ) {
        if (announcement != null) ShowEbook(
            idAnnouncement,
            announcement!!,
            userAuthor.value,
            it.calculateBottomPadding(),
            context
        )
    }
}

@Composable
fun ShowEbook(
    idAnnouncement: Int,
    announcement: AnnouncementGet,
    userAuthor: Boolean,
    bottomBarLength: Dp,
    context: Context
) {

    val context = LocalContext.current
    val userID = UserIDrepository(context).getAll()[0].idUser

    var likeState by remember {
        mutableStateOf(announcement.curtido)
    }

    var saveState by remember{
        mutableStateOf(announcement.favorito)
    }

    var viewState by remember{
        mutableStateOf(announcement.lido)
    }

    var quantidadeLikesState by remember {
        mutableStateOf("")
    }

    var quantidadeFavoritosState by remember {
        mutableStateOf("")
    }

    var quantidadeViewsState by remember {
        mutableStateOf("")
    }


    CallLikeAPI.countAnnouncementLikes(announcement.id!!) {
        quantidadeLikesState = it.qtdeCurtidas
    }

    CallFavoriteAPI.countFavoritesAnnouncement(announcement.id!!) {
        quantidadeFavoritosState = it.qtdeFavoritos
    }

    CallVisualizationAPI.countViewAnnouncement(announcement.id!!) {
        quantidadeViewsState = it.qtdeLidos
    }

    var pageState by remember {
        mutableStateOf(false)
    }

    var vendaState by remember {
        mutableStateOf(false)
    }

    var volumeState by remember {
        mutableStateOf(false)
    }

    var postState by remember {
        mutableStateOf(false)
    }

    val priceVerify = announcement.preco.toString().split('.')
    var price = announcement.preco.toString()
    val mouths = listOf<String>(
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
    val date = announcement.data.split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]


    //Card de informações do usuario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            thickness = .5.dp,
            color = Color.White
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp),
            shape = RoundedCornerShape(
                bottomEnd = if (userAuthor) 40.dp else 0.dp,
                bottomStart = if (userAuthor) 40.dp else 0.dp
            ),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(28.dp, 12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(announcement.capa),
                    contentDescription = "capa do livro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(98.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column() {

                        Text(
                            text = announcement.titulo,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Light,
                            fontFamily = SpartanBold,
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.eulirio_black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        LazyRow() {
                            items(announcement.generos) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp),
                                    backgroundColor = Color(0xFF1B0C36),
                                    shape = RoundedCornerShape(100.dp),
                                ) {
                                    Text(
                                        text = it.nome.uppercase(),
                                        fontSize = 10.sp,
                                        fontFamily = MontSerratSemiBold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(12.dp, 1.dp),
                                        color = Color.White
                                    )

                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        //Sistema de avaliação
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Outlined.StarOutline,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(text = "(4,5)")

                        }
                    }

                    Row() {
                        //Coluna de curtir
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário curtiu a publicação
                                if (likeState) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        modifier = Modifier
                                            .clickable {
                                                likeState = false

                                                val announcementDislike = LikeAnnouncement(
                                                    idAnuncio = announcement.id,
                                                    idUsuario = userID
                                                )

                                                CallLikeAPI.dislikeAnnouncement(announcementDislike)

                                                var newUnlikeConvert = quantidadeLikesState.toInt() - 1
                                                quantidadeLikesState = newUnlikeConvert.toString()

                                            },
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                    )
                                } else Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "icone de curtir",
                                    modifier = Modifier
                                        .clickable {
                                            likeState = true
                                            val announcementLike = LikeAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )

                                            CallLikeAPI.likeAnnouncement(announcementLike)

                                            var newlikeConvert = quantidadeLikesState.toInt() + 1
                                            quantidadeLikesState = newlikeConvert.toString()
                                        },
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                )

                                Text(
                                    text = "curtidas",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = quantidadeLikesState,
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de favoritar
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário favoritou a publicação
                                if (saveState) {
                                    Icon(
                                        Icons.Outlined.Bookmark,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable {
                                                saveState = false

                                                val announcementUnFavorite = FavoriteAnnouncement(
                                                    idAnuncio = announcement.id,
                                                    idUsuario = userID
                                                )

                                                CallFavoriteAPI.unfavoriteAnnouncement(
                                                    announcementUnFavorite
                                                )

                                                var newUnSaveConvert = quantidadeFavoritosState.toInt() - 1
                                                quantidadeFavoritosState = newUnSaveConvert.toString()

                                            },
                                        tint = Color.White
                                    )
                                } else Icon(
                                    Icons.Outlined.BookmarkAdd,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable {
                                            saveState = true



                                            val announcementFavorite = FavoriteAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.favoriteAnnouncement(
                                                announcementFavorite
                                            )

                                            var newSaveConvert = quantidadeFavoritosState.toInt() + 1
                                            quantidadeFavoritosState = newSaveConvert.toString()

                                        },
                                    tint = Color.White
                                )

                                Text(
                                    text = "favoritos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = quantidadeFavoritosState,
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        //Coluna de marcar como lido
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            ) {

                                //Verificação se o usuário marcou como lido a publicação
                                if (viewState) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de salvar",
                                        modifier = Modifier
                                            .clickable {
                                                viewState = false

                                                val unViewAnnouncement = VisualizationAnnouncement(
                                                    idAnuncio = announcement.id,
                                                    idUsuario = userID
                                                )
                                                CallVisualizationAPI.unViewAnnouncement(unViewAnnouncement)

                                                var newUnViewConvert = quantidadeViewsState.toInt() - 1
                                                quantidadeViewsState = newUnViewConvert.toString()

                                                       },
                                        tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )
                                } else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de salvar",
                                    modifier = Modifier
                                        .clickable {
                                            viewState = true

                                            val viewAnnouncement = VisualizationAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.viewAnnouncement(viewAnnouncement)

                                            var newViewConvert = quantidadeViewsState.toInt() + 1
                                            quantidadeViewsState = newViewConvert.toString()


                                                   },
                                    tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                )

                                Text(
                                    text = "lidos",
                                    fontSize = 10.sp,
                                    fontFamily = QuickSand,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            Text(
                                text = quantidadeViewsState,
                                fontSize = 16.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (!userAuthor) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp),
                elevation = 0.dp
            ) {
                Row(Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .fillMaxHeight(),
                        backgroundColor = Color(0xDFBDB5A),
                        shape = RoundedCornerShape(bottomStart = 40.dp),
                        border = BorderStroke(
                            .5.dp,
                            colorResource(id = R.color.eulirio_yellow_card_background)
                        ),
                        elevation = 0.dp
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (priceVerify[1].isEmpty())
                                price = "${priceVerify[0]}.00"
                            else if (priceVerify[1].length == 1)
                                price = "${announcement.preco}0"

                            Text(
                                text = "R$ ${price.replace('.', ',')}",
                                modifier = Modifier.fillMaxWidth(),
                                color = colorResource(id = R.color.eulirio_yellow_card_background),
                                textAlign = TextAlign.Center,
                                fontSize = 28.sp,
                                fontFamily = Spartan
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Toast
                                    .makeText(context, "oi", Toast.LENGTH_SHORT)
                                    .show()
                            },
                        shape = RoundedCornerShape(0.dp),
                        backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                        elevation = 0.dp
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.ebook_buy).uppercase(),
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraLight,
                                style = MaterialTheme.typography.h2
                            )
                        }
                    }
                }
            }
        }


//        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Column(
            Modifier
                .height(1000.dp)
                .padding(15.dp)
        ) {
            Row {
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
            Spacer(modifier = Modifier.width(25.dp))
            Spacer(modifier = Modifier.height(25.dp))

            //row dos icones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                //coluna de páginas
                Column() {
                    Row() {
                        Icon(
                            Icons.Outlined.Description,
                            contentDescription = "icone de páginas",
//                        modifier = Modifier
//                            .clickable { pageState = !pageState },
                            tint = Color.Black
                        )
                        Text(
                            text = "páginas (pdf)",
                            fontSize = 10.sp,
                            fontFamily = QuickSand,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                    Text(
                        text = announcement.qunatidadePaginas.toString(),
                        fontSize = 16.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                }

                Spacer(modifier = Modifier.width(20.dp))

                //coluna de volumes
                Column() {
                    Row() {
                        Icon(
                            Icons.Outlined.LibraryBooks,
                            contentDescription = "icone de volume",
//                        modifier = Modifier
//                            .clickable { volumeState = !volumeState },
                            tint = Color.Black
                        )
                        Text(
                            text = "volume",
                            fontSize = 10.sp,
                            fontFamily = QuickSand,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                    Text(
                        text = announcement.volume.toString(),
                        fontSize = 16.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                //coluna de vendas
                Column() {
                    Row() {
                        Icon(
                            Icons.Outlined.ShoppingBag,
                            contentDescription = "icone de vendas",
//                        modifier = Modifier
//                            .clickable { vendaState = !vendaState },
                            tint = Color.Black
                        )

                        Text(
                            text = "vendas",
                            fontSize = 10.sp,
                            fontFamily = QuickSand,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)

                        )
                    }
                    Text(
                        text = "1,2k",
                        fontSize = 16.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                }

                Spacer(modifier = Modifier.width(20.dp))

                //coluns de publicaçao
                Column() {
                    Row() {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = "icone de publicacoes",
//                        modifier = Modifier
//                            .clickable { postState = !postState },
                            tint = Color.Black
                        )
                        Text(
                            text = "publicação",
                            fontSize = 10.sp,
                            fontFamily = QuickSand,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                    Text(
                        text = "${date[2]} $mouth ${date[0]}",
                        fontSize = 12.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            //sinopse
            Column() {

                Text(
                    text = announcement.sinopse,
                    fontSize = 12.sp,
                    fontFamily = QuickSand,
                    modifier = Modifier.padding(start = 12.dp)

                )
                Spacer(modifier = Modifier.height(20.dp))

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp)
                )

                Row() {
                    Text(
                        text = "Classificação indicativa:",
                        fontFamily = Spartan,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = announcement.classificacao[0].classificacao,
                        fontFamily = SpartanBold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Column() {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Disponível em: ",
                            fontFamily = Spartan,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = if (announcement.mobi == "null") "PDF e ePUB" else "PDF, ePUB e MOBI",
                            fontFamily = SpartanBold
                        )
                    }
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp)
                )
            }
        }
    }
}


@Composable
fun BottomBarEbook(
    bottomBarState: MutableState<Boolean>,
    userAuthor: Boolean,
    context: Context,
    navController: NavController,
    idAnnouncement: Int?,
    userID: Int
) {

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            if (userAuthor) {
                BottomAppBar(
                    modifier = Modifier.clickable {
                        navController.navigate("${Routes.EditEbook.name}/${idAnnouncement!!}")
                    },
                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Text(
                        text = stringResource(R.string.ebook_edit).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth(),
//                                .padding(end = 44.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        style = MaterialTheme.typography.h2
                    )
                }
            } else {
                BottomAppBar(
                    contentPadding = PaddingValues(0.dp),
                    elevation = 0.dp
//                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Row(Modifier.fillMaxSize()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .fillMaxHeight()
                                .clickable {
                                    val cart = idAnnouncement?.let {
                                        Cart(
                                            anuncioID = it,
                                            userID = userID
                                        )
                                    }

                                    CallCartAPI.putInCart(cart!!) {
                                        Toast
                                            .makeText(context, it, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        ) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.ebook_bag).uppercase(),
                                    color = colorResource(id = R.color.eulirio_yellow_card_background),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    Toast
                                        .makeText(context, "oi", Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(0.dp),
                            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                            elevation = 0.dp
                        ) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.ebook_buy).uppercase(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    style = MaterialTheme.typography.h2
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
fun TopBarEbook(
    title: String,
    topBarState: MutableState<Boolean>,
    context: Context,
    userAuthor: Boolean,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = topBarState.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title.uppercase(),
                            modifier = Modifier
                                .fillMaxWidth(.8f),
//                                .padding(end = 44.dp),
                            color = colorResource(id = R.color.eulirio_black),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
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
                            tint = if (userAuthor) Color.Transparent else colorResource(
                                id = R.color.eulirio_black
                            )
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
                backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
                elevation = 0.dp
            )
        }
    )
}



@Composable
fun DefaultPreview6() {
    LoginPageTheme {
        EbookView(1, rememberNavController())
    }
}