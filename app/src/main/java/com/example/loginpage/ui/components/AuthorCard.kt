package com.example.loginpage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.R
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.MontSerratSemiBold

@Composable
fun AuthorCard(
    user: User,
    navController: NavController
) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 2.dp)
            .clickable {
                navController.navigate("${Routes.User.name}/${user.id}")
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Column() {
                    Image(
                        painter = rememberAsyncImagePainter(user.foto), 
                        contentDescription = "foto do usuário"
                    )
                    Row() {
                        Text(
                            text = user.nome,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Text(
                            text = user.userName
                        )
                    }
                }

                LazyRow() {
                    items(items = user.generos) {
                        Card(
                            modifier = Modifier
                                .height(14.dp)
                                .padding(start = 4.dp, end = 4.dp)
                            ,
                            backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                            shape = RoundedCornerShape(100.dp),
                        ) {
                            Text(
                                text = it.nomeGenero.uppercase(),
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
        }
    }
}