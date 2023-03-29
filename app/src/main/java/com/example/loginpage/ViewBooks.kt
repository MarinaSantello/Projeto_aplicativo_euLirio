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
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class ViewBooks : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowBooks()
                }
            }
        }
    }
}


@Composable
fun ShowBooks() {

    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember{
        mutableStateOf(false)
    }

    var viewState by remember{
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFAF0B8)),

    ) {

        //Header
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.eulirio_beige_color_background))

        ) {
            //Gerar a header
            Row(
                modifier = Modifier
                    .height(70.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Foto de perfil do usuario
                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    backgroundColor = Color.Black
                ) {}

                Image(
                    painter = painterResource(id = R.drawable.logo_icone_eulirio),
                    contentDescription = "logo aplicativo",
                    modifier = Modifier
                        .height(90.dp)
                )

                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                ) {}
            }
            //Layout do perfil
            TabsFiltro()
        }


        Card(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            elevation = 0.dp
        ){
            //Criação dos cards
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(Color(0xffFAF0B8))
            ) {

                Column(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Lorem Ipsum",
                        fontSize = 16.sp,
                        fontFamily = SpartanBold
                    )
                    Row() {
                        Text(
                            text = "Escrito por ",
                            fontSize = 10.sp,
                            fontFamily = SpartanExtraLight,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = "n.Sebastian",
                            fontSize = 10.sp,
                            fontFamily = SpartanMedium
                        )

                    }
                }

                Row() {
                    //Imagem da capa do livro
                    Card(
                        modifier = Modifier
                            .height(150.dp)
                            .width(100.dp),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.Black
                    ) {}

                    Spacer(modifier = Modifier.width(9.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        //Sistema de avaliação
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "estrela de avaliação",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "estrela de avaliação",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "estrela de avaliação",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "estrela de avaliação",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "estrela de avaliação",
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )

                        }

                        val genres = listOf<String>("TERROR", "DRAMA", "SUSPENSE")

                        LazyRow() {
                            items(genres) {
                                Card(
                                    modifier = Modifier
//                            .padding(end = 6.dp)
                                        .height(14.dp)
                                        .padding(start = 5.dp, end = 5.dp)
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
                                            .padding(start = 5.dp, end = 5.dp)
                                    )

                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer lorem elit, congue sed tincidunt id, maximus sagittis leo. Curabitur ultricies elit sem, ac consequat enim posuere vel. Fusce eget condimentum enim, vel aliquam augue. ...",
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "R$ 20,00",
                                fontSize = 15.sp,
                                fontFamily = Roboto,
                                fontWeight = FontWeight.Bold
                            )

                            Row() {
                                //Linha de curtir
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable { likeState = true }
                                ){

                                    //Verificação se o usuário curtiu a publicação
                                    if(likeState){
                                        Icon(
                                            Icons.Outlined.Favorite,
                                            contentDescription = "icone de curtir",
                                            tint = Color.Red
                                        )
                                    }

                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        modifier = Modifier,
                                        tint = Color.Red
                                    )

                                    Text(
                                        text = "570",
                                        fontSize = 10.sp,
                                        fontFamily = Montserrat2,
                                        fontWeight = FontWeight.W500,
                                    )
                                }

                                //Linha de favoritar
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable { saveState = true }
                                ){

                                    //Verificação se o favoritou a publicação
                                    if(saveState){
                                        Icon(
                                            Icons.Outlined.Bookmark,
                                            contentDescription = "icone de favoritar",
                                            tint = colorResource(id = R.color.eulirio_yellow_card_background)
                                        )
                                    }

                                    Icon(
                                        Icons.Outlined.BookmarkAdd,
                                        contentDescription = "icone de favoritar",
                                        tint = colorResource(id = R.color.eulirio_yellow_card_background)
                                    )

                                    Text(
                                        text = "570",
                                        fontSize = 10.sp,
                                        fontFamily = Montserrat2,
                                        fontWeight = FontWeight.W500
                                    )
                                }

                                //Linha de visualização
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable { viewState = true }
                                ){

                                    //Verificação se o usuário visualizou a publicação
                                    if(viewState){
                                        Icon(
                                            Icons.Rounded.CheckCircle,
                                            contentDescription = "icone de visualizacão",
                                            tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                        )
                                    }

                                    Icon(
                                        Icons.Outlined.CheckCircle,
                                        contentDescription = "icone de visualizacão",
                                        tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                                    )

                                    Text(
                                        text = "570",
                                        fontSize = 10.sp,
                                        fontFamily = Montserrat2,
                                        fontWeight = FontWeight.W500

                                    )
                                }
                            }


                        }

                    }

                }

            }

        }

    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview4() {
    LoginPageTheme {
        ShowBooks()
    }
}


@Composable
fun TabsFiltro() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Livros", "Pequenas Histórias", "Recomendações")



    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selectedContentColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unselectedContentColor = Color.Black,
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(
                                Icons.Outlined.MenuBook,
                                contentDescription = "icone de livro",
                                modifier = Modifier
                                    .height(12.dp), //tint = if (booksOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                            )
                            1 -> Icon(
                                Icons.Outlined.FormatAlignCenter,
                                contentDescription = "icone de texto",
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(end = 8.dp),
                                //tint = if (curtasOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
                            )
                            2 -> Icon(
                                Icons.Outlined.LocalLibrary,
                                contentDescription = "icone de pessoa lendo um livro",
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(end = 8.dp),
                                //tint = if (recomendationOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black2
                            )
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Text(text = "Livro")
            1 -> Text(text = "Pequenas Histórias")
            2 -> Text(text = "Pequenas Histórias")
        }
    }
}