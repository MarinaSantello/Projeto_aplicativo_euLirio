package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.ui.theme.LoginPageTheme
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
    Column(
        modifier = Modifier
            .fillMaxSize()
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

        //Criação dos cards
        Column(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
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
                    fontWeight = FontWeight.Bold
                )
                Row() {
                    Text(
                        text = "Escrito por ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    )

                    Text(
                        text = "n.Sebastian",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
            
            Row() {
                Card(
                    modifier = Modifier
                        .height(150.dp)
                        .width(100.dp)
                ) {

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
                                //tint = if (recomendationOnClickState) colorResource(id = R.color.eulirio_yellow_card_background) else Color.Black
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