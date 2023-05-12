package com.example.loginpage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.ShortStoryGet
import com.example.loginpage.ui.theme.*

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
                    ShowRecommendation()
                }
            }
        }
    }
}

@Composable
fun ShowRecommendation(
) {
    Column() {

        //TopBar no lugar do card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp),
            backgroundColor = colorResource(id = R.color.eulirio_beige_color_background),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row() {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    contentDescription = "setinha",
                    modifier = Modifier.padding(start = 12.dp, top = 14.dp),
                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                )
                Text(
                    text = "RECOMENDAÇÃO",
                    fontFamily = MontSerratBold,
                    modifier = Modifier.padding(top = 14.dp, start = 100.dp),
                    textAlign = TextAlign.Center
                )

                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = "menu",
                    modifier = Modifier.padding(start = 92.dp, top = 14.dp),
                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border))
            }



            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Column() {
                Text(
                    text = "Lorem Ipsum",
                    fontFamily = SpartanBold,
                    modifier = Modifier.padding(top = 60.dp, start = 130.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Escrito por n.sebastian",
                    fontFamily = Spartan,
                    modifier = Modifier.padding(start = 130.dp),
                    textAlign = TextAlign.Center
                )

                Card(
                    modifier = Modifier
                        .padding(start = 130.dp, end = 4.dp),
                    backgroundColor = Color(0xFF1B0C36),
                    shape = RoundedCornerShape(100.dp),
                ) {

                    Text(
                        text = "TERROR",
                        textAlign = TextAlign.Center,
                        fontSize = 8.sp,
                        fontFamily = MontSerratSemiBold,
                        modifier = Modifier
                            .padding(start = 8.dp),
                        color = Color.White
                    )

                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
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
            Row {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "foto da pessoa",
                    modifier = Modifier.padding(top = 200.dp, start = 22.dp)
                )


                Column {
                    Text(
                        text = "Nome do usuário",
                        fontSize = 17.sp,
                        fontFamily = SpartanMedium,
                        modifier = Modifier.padding(top = 200.dp, start =16.dp),
                        textAlign = TextAlign.Justify

                    )

                    Row(){

                        Text(
                            text = "@user do usuário",
                            fontSize = 14.sp,
                            fontFamily = SpartanExtraLight,
                            modifier = Modifier.padding(top = 4.dp, start = 16.dp)
                        )

                        Text(text = "12 Jun 2022",
                            modifier = Modifier.padding(start = 160.dp),
                            fontFamily = SpartanExtraLight,
                            textAlign = TextAlign.End,
                            fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.padding(vertical = 12.dp) )

                    //sinopse
                    Text(text = "sinopse",
                        fontFamily = QuickSand,
                        fontSize = 12.sp

                    )

                }
            }



        }

    }


    //BottomBar
    Card(
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
                    .padding(top = 8.dp )
                    .size(20.dp),
                tint = colorResource(id = R.color.eulirio_yellow_card_background)
            )
            Text(text = "182",
                Modifier.padding(top = 8.dp),
                fontFamily = QuickSand, fontSize = 16.sp)
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