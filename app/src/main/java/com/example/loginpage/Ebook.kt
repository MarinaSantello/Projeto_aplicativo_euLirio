package com.example.loginpage

import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
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
import com.example.loginpage.API.buy.CallBuyAPI
import com.example.loginpage.API.cart.CallCartAPI
import com.example.loginpage.API.comment.CallCommentAPI
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.recommendation.CallRecommendationAPI
import com.example.loginpage.API.stripe.CallStripeAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.CommentCard
import com.example.loginpage.ui.components.ComplaintCard
import com.example.loginpage.ui.theme.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

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

    val deleteState = remember {
        mutableStateOf(false)
    }

    if (deleteState.value || !deleteState.value) Scaffold(
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
                userID,
                idAnnouncement,
                context,
                0,
                userAuthor.value,
                navController
            )
        },
        bottomBar = {
            BottomBarEbook(
                bottomBarState,
                userAuthor.value,
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
            navController,
            deleteState
        )
    }
}

@Composable
fun ShowEbook(
    idAnnouncement: Int,
    announcement: AnnouncementGet,
    userAuthor: Boolean,
    bottomBarLength: Dp,
    navController: NavController,
    deleteState: MutableState<Boolean>
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

    var urlStripeState by remember{
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

    val priceVerify = announcement.preco.toString().split('.')
    var price = announcement.preco.toString()

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
    val date = announcement.data.split("T")[0].split("-")
    val mouth = mouths[(date[1].toInt() - 1)]

    val filledStars = floor(announcement.avaliacao).toInt()
    val unfilledStars = (5 - ceil(announcement.avaliacao)).toInt()
    val halfStar = !(announcement.avaliacao.rem(1).equals(0.0))

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
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.eulirio_black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        LazyRow() {
                            if (!(announcement.generos.isNullOrEmpty())) items(announcement.generos) {
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

                            Text(text = "(${announcement.avaliacao})".replace('.', ','))

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

                                                val newUnlikeConvert = quantidadeLikesState.toInt() - 1
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
                                fontSize = 15.sp,
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
                                fontSize = 15.sp,
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

                                            val viewAnnouncement = VisualizationAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.viewAnnouncement(viewAnnouncement)

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
                                fontSize = 15.sp,
                                fontFamily = MontSerratSemiBold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (!userAuthor) {

            var stripeId by remember {
                mutableStateOf("")
            }

            val resultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                }

                navController.navigate(Routes.PurchasedAnnouncements.name)

                val confirmBuy = BuyConfirm(
                    anuncioID = idAnnouncement!!,
                    userID = userID!!,
                    stripeID = stripeId
                )

                CallBuyAPI.confirmBuyAnnouncement(confirmBuy){

                }
            }

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
                            .fillMaxWidth(if (announcement.carrinho) 1f else .5f)
                            .fillMaxHeight(),
                        backgroundColor = Color(0xDFBDB5A),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = if(announcement.carrinho) 40.dp else 0.dp),
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

                    if (announcement.comprado) Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navController.navigate("${Routes.RecommendationPost.name}/$idAnnouncement")
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
                                text = stringResource(R.string.ebook_recommendation).uppercase(),
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraLight,
                                style = MaterialTheme.typography.h2
                            )
                        }
                    }



                    //Para efetuar a compra do anuncio
                    if (!announcement.carrinho) Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                val buy = Buy(
                                    anuncioID = idAnnouncement!!
                                )

                                CallBuyAPI.buyAnnouncement(userID, buy) {
                                    if (!it.url.isNullOrEmpty()) Toast
                                        .makeText(
                                            context,
                                            "Compra realizada.",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()

                                    val urlIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(it.url)
                                    )

                                    stripeId = it.intentId
                                    resultLauncher.launch(urlIntent)
                                }


//                                var idAnnounceToBuy = IdBuy(
//                                    id = announcement.id!!
//                                )
//
//                                val buyAtStripe = BuyAnnouncement(
//                                  idAnuncio = idAnnounceToBuy
//                                )
//
//                                CallStripeAPI.buyAnnouncementStripe(userID, buyAtStripe){
//                                    Log.i("url", it.url)
//                                    urlStripeState = it.url
//
//                                }

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
                .heightIn(550.dp)
                .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = bottomBarLength)
//                .verticalScroll(rememberScrollState())
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
                        text = if(announcement.compras != null) announcement.compras!!.qtdeCompras else "0",
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
                    fontSize = 14.sp,
                    fontFamily = QuickSand,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)

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

                Spacer(modifier = Modifier.height(12.dp))

                var comments by remember {
                    mutableStateOf(listOf<Commit>())
                }
                var commentsIsNull by remember {
                    mutableStateOf(false)
                }

                CallCommentAPI.getCommentsAnnouncement(announcement.id!!, userID) {
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
                            text = "Avaliações do livro",
                            fontFamily = SpartanBold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "(${announcement.comentarios.qtdComentarios})",
                            fontFamily = SpartanBold,
                            fontSize = 16.sp
                        )
                    }


                    if (announcement.comprado && !announcement.comentado) Card(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                            .clickable {
                                navController.navigate("${Routes.CommitAnnPost.name}/${announcement.id}")
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

                val idAuthorAnnouncement = announcement.usuario[0].idUsuario!!.toInt()

                if (comments.isNotEmpty()) Column() {
                    for (i in 0 until comments.size) {
                        Log.i("id anuncio $i", comments[i].id.toString())

                        CommentCard(comments[i], navController, userID, idAnnouncement){
                            deleteState.value = it

                            if (it)comments -= comments[i]
                        }
                    }
                }

                if (commentsIsNull) Text(text = if(announcement.usuario[0].idUsuario == userID) "A sua história ainda não tem avaliações." else "Essa história ainda não tem avaliações. Leia-a e seja o primeiro!")
            }
        }
    }
}




@Composable
fun BottomBarEbook(
    bottomBarState: MutableState<Boolean>,
    userAuthor: Boolean,
    navController: NavController,
    idAnnouncement: Int?,
    userID: Int
) {
    val context = LocalContext.current

    var stripeId by remember {
        mutableStateOf("")
    }

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
        }

        navController.navigate(Routes.PurchasedAnnouncements.name)

        val confirmBuy = BuyConfirm(
            anuncioID = idAnnouncement!!,
            userID = userID!!,
            stripeID = stripeId
        )
        CallBuyAPI.confirmBuyAnnouncement(confirmBuy){

        }
    }

    var idAnuncio = 0

    idAnnouncement?.let {
        idAnuncio = it
    }

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(idAnuncio, userID) {
        announcement = it
    }

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
                if (announcement?.carrinho == true) BottomAppBar(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.ShoppingCart.name}/$userID")
                        },
                    contentPadding = PaddingValues(0.dp),
                    backgroundColor = Color.White,
                    elevation = 0.dp,
//                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Text(
                        text = stringResource(R.string.ebook_cart).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = colorResource(id = R.color.eulirio_yellow_card_background),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        style = MaterialTheme.typography.h2
                    )
                }

                else if(announcement?.comprado == true) BottomAppBar(
                    modifier = Modifier.clickable {
                        val httpsReference = announcement?.pdf?.let {
                            FirebaseStorage
                                .getInstance()
                                .getReferenceFromUrl(it)
                        }

                        val pdf = announcement?.titulo?.let { File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$it.pdf") }
                        val epub = announcement?.titulo?.let { File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$it.epub") }
                        val mobi = announcement?.titulo?.let { File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$it.mobi") }

                        if (epub != null && httpsReference != null) {
                            httpsReference.getFile(epub)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "download completo", Toast.LENGTH_SHORT).show()
                                    Log.i("download pdf", "hmkk")
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "porra sefodeukk", Toast.LENGTH_SHORT).show()
                                    Log.i("download pdf err", "hmkk")
                                }
                        }
                    },
                    backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background)
                ) {
                    Text(
                        text = stringResource(R.string.ebook_download).uppercase(),
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

                else BottomAppBar(
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
                                    val cart = Cart(
                                        idAnuncio = listOf(CartItems(idAnuncio))
                                    )

                                    CallCartAPI.putInCart(userID, cart) {
                                        if (it == 200) Toast
                                            .makeText(
                                                context,
                                                "e-book adicionado ao carrinho",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()

                                        navController.navigate("${Routes.Ebook.name}/${announcement?.id}")
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
                                    val buy = Buy(
                                        anuncioID = idAnnouncement!!
                                    )

                                    CallBuyAPI.buyAnnouncement(userID, buy) {
                                        if (!it.url.isNullOrEmpty()) {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Compra realizada.",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()

                                            val urlIntent = Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(it.url)
                                            )

                                            stripeId = it.intentId
                                            resultLauncher.launch(urlIntent)
                                        }
                                    }
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
    userID: Int,
    complaintID: Int,
    context: Context,
    typeComplaint: Int,
    userAuthor: Boolean,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    val showDialog = remember { mutableStateOf(false) }
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
                                .clickable {
                                    showDialog.value = true
                                },
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

    ComplaintCard(showDialog, userID, complaintID, typeComplaint) {
        if (it) showDialog.value = false
    }
}


@Composable
fun DefaultPreview6() {
    LoginPageTheme {
        EbookView(1, rememberNavController())
    }
}