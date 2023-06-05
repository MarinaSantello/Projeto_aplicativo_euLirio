package com.example.loginpage

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.example.loginpage.API.comment.CallCommentAPI
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.resources.TopBar
import com.example.loginpage.ui.components.CommentCard
import com.example.loginpage.ui.components.CommentCardSS
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor

class ShortStories : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShortStory(1, rememberNavController())
                }
            }
        }
    }
}

@Composable
fun ShortStory(
    shortStoryId: Int,
    navController: NavController
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }
    var userAuthor by remember { mutableStateOf(false) }

    val deleteState = remember {
        mutableStateOf(false)
    }

    // registrando o id do usuário no sqlLite
    val userID = UserIDrepository(context).getAll()[0].idUser

    var shortStory by remember {
        mutableStateOf<ShortStoryGet?>(null)
    }

    CallShortStoryAPI.getShortStoryByID(shortStoryId, userID) {
        shortStory = it

        userAuthor = (it.usuario[0].idUsuario == userID)
    }

    if (deleteState.value || !deleteState.value)Scaffold(
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
        topBar = { TopBarEbook(stringResource(R.string.title_short_story), topBarState, userID, shortStoryId, context, 1, userAuthor, navController) },
        bottomBar = { if (shortStory != null) BottomBarShortStory(bottomBarState, context, navController, shortStory!!, userID) },
    ) {
        if (shortStory != null) ShowStories(shortStory!!, it.calculateBottomPadding(), navController, context, deleteState)
    }
}

@Composable
fun ShowStories(
    shortStory: ShortStoryGet,
    bottomBarLength: Dp,
    navController: NavController,
    context: Context,
    deleteState: MutableState<Boolean>
) {

    val userID = UserIDrepository(context).getAll()[0].idUser

    var likeState by remember {
        mutableStateOf(shortStory.curtido)
    }

    var saveState by remember {
        mutableStateOf(shortStory.favorito)
    }
    var viewState by remember {
        mutableStateOf(shortStory.lido)
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

    CallLikeAPI.countShortStoriesLikes(shortStory.id!!){
        quantidadeLikesState = it.qtdeCurtidas
    }

    CallFavoriteAPI.countFavoritesShortStories(shortStory.id!!){
        quantidadeFavoritosState = it.qtdeFavoritos
    }

    CallVisualizationAPI.countViewShortStorie(shortStory.id!!){
        quantidadeViewsState = it.qtdeLidos
    }


    val mouths = listOf("Jan.", "Fev.", "Mar.", "Abr.", "Mai.", "Jun.", "Jul.", "Ago.", "Set.", "Out.", "Nov.", "Dez.")
    val date = shortStory.data.split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]

    val filledStars = floor(shortStory.avaliacao).toInt()
    val unfilledStars = (5 - ceil(shortStory.avaliacao)).toInt()
    val halfStar = !(shortStory.avaliacao.rem(1).equals(0.0))

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
                bottomEnd = 40.dp,
                bottomStart = 40.dp
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
                    painter = rememberAsyncImagePainter(shortStory.capa),
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
                            text = shortStory.titulo,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Light,
                            fontFamily = SpartanBold,
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.eulirio_black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        LazyRow() {
                            items(shortStory.generos) {
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
                        Row() {
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

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(text = "(${shortStory.avaliacao})".replace('.', ','))

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

                                                var shortStorieUnLike = LikeShortStorie(
                                                    idHistoriaCurta = shortStory.id,
                                                    idUsuario = userID
                                                )
                                                CallLikeAPI.dislikeShortStorie(shortStorieUnLike)

                                                val newUnlikeConvert = quantidadeLikesState.toInt() - 1
                                                quantidadeLikesState = newUnlikeConvert.toString()

                                            },
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                    )
                                } else Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "icone de curtir",
                                    modifier = Modifier
                                        .clickable { likeState = true

                                            var shortStorieLike = LikeShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallLikeAPI.likeShortStorie(shortStorieLike)

                                            val newlikeConvert = quantidadeLikesState.toInt() + 1
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

                                                val favoriteShortStorieUnCheck = FavoriteShortStorie(
                                                    idHistoriaCurta = shortStory.id,
                                                    idUsuario = userID
                                                )
                                                CallFavoriteAPI.unfavoriteShortStorie(
                                                    favoriteShortStorieUnCheck
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

                                            val favoriteShortStorieCheck = FavoriteShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.favoriteShortStorie(
                                                favoriteShortStorieCheck
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

                                                val unViewShortStorie = VisualizationShortStorie(
                                                    idHistoriaCurta = shortStory.id,
                                                    idUsuario = userID
                                                )
                                                CallVisualizationAPI.unViewShortStorie(unViewShortStorie)

                                                val newUnViewConvert = quantidadeViewsState.toInt() - 1
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

                                            val viewShortStorie = VisualizationShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.viewShortStorie(viewShortStorie)

                                            val newViewConvert = quantidadeViewsState.toInt() + 1
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
        Spacer(modifier = Modifier.height(12.dp))

        Column (
            Modifier
                .heightIn(550.dp)
                .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = bottomBarLength)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(Modifier.clickable {
                        navController.navigate("${Routes.User.name}/${shortStory.usuario[0].idUsuario}")
                    }) {
                    Image(
                        painter = rememberAsyncImagePainter(shortStory.usuario[0].foto),
                        contentDescription = "foto da pessoa",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp)
                            .size(40.dp)
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                    )

                    Column() {
                        Text(
                            text = shortStory.usuario[0].nomeUsuario,
                            fontSize = 15.sp,
                            fontFamily = SpartanMedium,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)

                        )
                        Text(
                            text = "@${shortStory.usuario[0].userName}",
                            fontSize = 12.sp,
                            fontFamily = Spartan,
                            modifier = Modifier.padding(top = 2.dp, start = 8.dp)
                        )

                    }
                }
                Column() {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = "icone de publicacoes",
                            modifier = Modifier
                                .padding(start = 4.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "publicação",
                            fontSize = 10.sp,
                            fontFamily = QuickSand,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    Text(
                        text = "${date[2]} $mouth ${date[0]}",
                        fontSize = 12.sp,
                        fontFamily = MontSerratSemiBold,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }

            //sinopse
            Column(modifier = Modifier. padding(8.dp)) {
                Text(
                    text = shortStory.sinopse,
                    fontSize = 14.sp,
                    fontFamily = QuickSand,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(8.dp)
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Classificação indicativa:",
                        fontFamily = Spartan,
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = shortStory.classificacao[0].classificacao,
                        fontFamily = SpartanBold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                var comments by remember {
                    mutableStateOf(listOf<CommitSS>())
                }
                var commentsIsNull by remember {
                    mutableStateOf(false)
                }

                CallCommentAPI.getCommentsSS(shortStory.id!!, userID) {
                    if (it.isNullOrEmpty()) commentsIsNull = true
                    else comments = it
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ){


                    Row(
                        horizontalArrangement = Arrangement.Center
                    ){
                        Icon(
                            Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "icone de vendas",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(end = 15.dp)


                        )

                        Text(
                            text = "Avaliações do livro ",
                            fontFamily = SpartanBold,
                            fontSize = 16.sp
                        )

                        Text(
                            text ="(${shortStory.comentarios.qtdComentarios})",
                            fontFamily = SpartanBold,
                            fontSize = 16.sp
                        )
                    }


                    val idAuthorSS = shortStory.usuario[0].idUsuario

                    if (idAuthorSS != userID && !shortStory.comentado) Card(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                            .clickable {
                                navController.navigate("${Routes.CommitSSPost.name}/${shortStory.id}")
                            }
                        ,
                        backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        shape = RoundedCornerShape(100.dp),
                    ) {
                        Text(
                            text = "AVALIAR",
                            fontSize = 14.sp,
                            fontFamily = MontSerratSemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp, 0.dp),
                            color = Color.White
                        )
                    }
                }

                if (comments.isNotEmpty()) Column() {
                    for (i in 0 until comments.size) {
//                        Log.i("id anuncio $i", comments[i].id.toString())

                        CommentCardSS(comments[i], navController, userID, shortStory.id!!) {
                            deleteState.value = it

                            if (it)comments -= comments[i]
                        }
                    }
                }

                if (commentsIsNull) Text(text = if(shortStory.usuario[0].idUsuario == userID) "A sua história ainda não tem avaliações." else "Essa história ainda não tem avaliações. Leia-a e seja o primeiro!")

            }
        }
    }
}

@Composable
fun BottomBarShortStory(
    bottomBarState: MutableState<Boolean>,
    context: Context,
    navController: NavController,
    shortStory: ShortStoryGet,
    userID: Int
) {

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {

        BottomAppBar(
            modifier = Modifier.clickable {
                navController.navigate("${Routes.ShortStoryRender.name}/${shortStory.id}/$userID")
            },
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
        ) {
            Text(
                text = stringResource(R.string.read_short_story).uppercase(),
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraLight,
                style = MaterialTheme.typography.h2
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview6() {
//    LoginPageTheme
//    }
//}