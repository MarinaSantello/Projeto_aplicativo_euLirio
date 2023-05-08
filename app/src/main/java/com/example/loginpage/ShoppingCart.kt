package com.example.loginpage

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.cart.CallCartAPI
import com.example.loginpage.API.shortStory.CallShortStoryAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.resources.BottomBarScaffold
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.AnnouncementCard
import com.example.loginpage.ui.components.AnnouncementCart
import com.example.loginpage.ui.components.ShortStorysCard
import com.example.loginpage.ui.theme.*
import kotlinx.coroutines.launch

//class ShoppingCart : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginPageTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    ShoppingCartPage(rememberNavController(), 0)
//                }
//            }
//        }
//    }
//}

@Composable
fun ShoppingCartPage(
    navController: NavController,
    userID: Int
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBarShop(navController) },
        bottomBar = { BottomBarScaffold(bottomBarState, navController, userID, 3) }
    ) {
        ShowBooks(userID, it.calculateBottomPadding(), 3, topBarState, bottomBarState, navController, null)
    }
}

@Composable
fun ShowItemsCart(
    userID: Int,
    bottomBarLength: Dp,
    navController: NavController
) {
    val context = LocalContext.current

    var tabIndex by remember { mutableStateOf(0) }
    //= rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember {
        mutableStateOf(false)
    }

    val tabs = listOf("Carrinho", "Favoritos")

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
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
                var itemsCarts by remember {
                    mutableStateOf(listOf<CartData>())
                }
                var totalCart by remember {
                    mutableStateOf(0f)
                }
                var cartIsNull by remember {
                    mutableStateOf(false)
                }

                CallCartAPI.getItemsCart(userID) {
                    if (it == null) cartIsNull = true

                    else {
                        itemsCarts = it.items
                        totalCart = it.total
                    }
                }


                val priceVerify = totalCart.toString().split('.')
                var price = totalCart.toString()

                if (priceVerify[1].isEmpty())
                    price = "${priceVerify[0]}.00"
                else if (priceVerify[1].length == 1)
                    price = "${totalCart}0"

                if (cartIsNull) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Seu carrinho está vazio!",
                        textAlign = TextAlign.Center,
                        fontFamily = MontSerratBold
                    )
                }

                else Box(Modifier.fillMaxSize()) {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = bottomBarLength + 50.dp)
                    ) {
                        items(itemsCarts) {
                            AnnouncementCart(it, userID, navController, bottomBarLength, 0, context)
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                            ,
                            shape = RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp),
                            backgroundColor = Color(254, 252, 241, 255)

                        ) {

                            Column(Modifier.fillMaxSize()) {
                                Card(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                        .border(
                                            width =  1.dp,
                                            color = colorResource(id = R.color.eulirio_yellow_card_background),
                                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                        ),
                                    elevation = 0.dp
                                ) {}
                                Row(Modifier.fillMaxWidth()) {

                                    Text(text = "Total",
                                        fontFamily = SpartanBold,
                                        fontSize = 22.sp ,
                                        modifier = Modifier.padding(start = 12.dp, top = 8.dp
                                        ))

                                    Text( text ="R$ $price",
                                        fontFamily = SpartanRegular,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 20.dp, top = 8.dp),
                                        textAlign = TextAlign.End,
                                        fontSize = 22.sp
                                    )
                                }

                                Spacer(modifier = Modifier.padding(8.dp))

                                Button(onClick = {
                                    var announcements = listOf<CartItems>()

                                    for (element in itemsCarts) {
                                        announcements += CartItems(element.anuncioID)
                                    }

                                    val buyItems = Cart(
                                        idAnuncio = announcements
                                    )
                                    CallCartAPI.buyItemsCart(userID, buyItems) {
                                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                },
                                    modifier = Modifier
                                        .width(350.dp)
                                        .padding(start = 40.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_yellow_card_background))
//

                                ) {
                                    Text(text = "FINALIZAR COMPRA",
                                        fontFamily = MontSerratSemiBold,
                                        color = Color.White

                                    )
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                var announcements by remember {
                    mutableStateOf(listOf<AnnouncementGet>())
                }
                var announcementIsNull by remember {
                    mutableStateOf(false)
                }

                CallAnnouncementAPI.getUserFavoritedAnnouncements(userID) {
                    if (it.isNullOrEmpty()) announcementIsNull = true
                    else announcements = it
                }

                var cartIsNull by remember {
                    mutableStateOf(true)
                }
                if (announcementIsNull) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Você não possui livros favoritados.",
                        textAlign = TextAlign.Center,
                        fontFamily = MontSerratBold
                    )
                }

                else LazyColumn(
                    contentPadding = PaddingValues(bottom = bottomBarLength)
                ) {
                    items(announcements) {
                        val priceVerify = it.preco.toString().split('.')
                        var price = it.preco.toString()

                        if(!it.comprado && !it.carrinho) Card(
                            modifier = Modifier
                                .height(204.dp)
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                                .clickable {
                                    navController.navigate("${Routes.Ebook.name}/${it.id}")
                                },
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        ) {
                            cartIsNull = false
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp, 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //Imagem da capa do livro
                                Image(
                                    painter = rememberAsyncImagePainter(it.capa),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(150.dp)
                                        .width(100.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop,
                                )

                                Column (
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(top = 4.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()


                                    ) {
                                        val files = listOf("PDF", "ePUB", if (it.mobi == "null") "" else "MOBI")

                                        Text(text = it.titulo,
                                            fontFamily = SpartanBold,
                                            modifier = Modifier.padding(start = 8.dp, top = 20.dp),
                                            fontSize = 18.sp
                                        )

                                        Spacer(modifier = Modifier.padding(vertical = 6.dp))

                                        LazyRow(contentPadding = PaddingValues(bottom = bottomBarLength)) {
                                            items(files) {
                                                if (it.isNotEmpty()) Card(
                                                    modifier = Modifier
                                                        .height(14.dp)
                                                        .padding(start = 4.dp, end = 4.dp)
                                                    ,
                                                    backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                                                    shape = RoundedCornerShape(100.dp),
                                                ) {
                                                    Text(
                                                        text = it,
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
                                    }

                                    if (priceVerify[1].isEmpty())
                                        price = "${priceVerify[0]}.00"
                                    else if (priceVerify[1].length == 1)
                                        price = "${it.preco}0"

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "R$ ${price.replace('.', ',')}",
                                            modifier = Modifier.padding(bottom = 20.dp, start = 12.dp),
                                            fontFamily = SpartanRegular
                                        )

                                        Card(
                                            Modifier
                                                .width(60.dp)
                                                .height(32.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .border(
                                                    1.dp,
                                                    colorResource(R.color.eulirio_yellow_card_background),
                                                    RoundedCornerShape(20.dp)
                                                )
                                                .clickable {
                                                    val cart = Cart(
                                                        idAnuncio = listOf(CartItems(it.id!!))
                                                    )

                                                    CallCartAPI.putInCart(
                                                        userID,
                                                        cart
                                                    ) { statusCode ->
                                                        if (statusCode == 200) {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "e-book adicionado ao carrinho",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()

                                                            navController.navigate("${Routes.ShoppingCart.name}/$userID")
                                                        }
                                                    }
                                                }
                                        ) {
                                            Icon(
                                                Icons.Rounded.ShoppingCart,
                                                contentDescription = "carrinho de comprar"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (cartIsNull)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Você não possui livros favoritados disponíveis para compra.",
                            textAlign = TextAlign.Center,
                            fontFamily = MontSerratBold
                        )
                    }
            }
        }
    }
}



@Composable
fun TopBarShop (
    navController: NavController
) {

    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo_icone_eulirio),
                contentDescription = "logo aplicativo",
                modifier = Modifier
                    .fillMaxWidth(),
//                            .padding(end = 56.dp),
                alignment = Alignment.Center
            )
        },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            coroutineScope.launch {
//                                navController.popBackStack()
//                            }
//                        }
//                    ) {
//                        Icon(
//                            Icons.Rounded.ChevronLeft,
//                            contentDescription = "botao para voltar",
//                            modifier = Modifier
//                                .fillMaxSize(.8f)
//                                .padding(start = 12.dp)
//                                .clip(RoundedCornerShape(100.dp)),
//                            tint = colorResource(id = R.color.eulirio_black)
//                        )
//                    }
//                },
        backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginPageTheme {

    }
}