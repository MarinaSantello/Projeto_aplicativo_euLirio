package com.example.loginpage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.R
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.ui.theme.MontSerratSemiBold
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanExtraLight
import com.example.loginpage.ui.theme.SpartanMedium
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun CardAnnouncementRecommended(
    announcement: AnnouncementGet
) {

    val filledStars = floor(announcement.avaliacao).toInt()
    val unfilledStars = (5 - ceil(announcement.avaliacao)).toInt()
    val halfStar = !(announcement.avaliacao.rem(1).equals(0.0))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(102.dp)
    ){
        Row(
            modifier = Modifier.padding(8.dp)
        ){
            //Imagem da capa do livro
            Image(
                painter = rememberAsyncImagePainter(announcement.capa),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(57.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier.padding(start = 5.dp)
            ){
                Text(
                    text = announcement.usuario[0].nomeUsuario,
                    fontSize = 11.sp,
                    fontFamily = SpartanBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.Bottom

                ){
                    Text(
                        text = "Escrito por ",
                        fontSize = 7.sp,
                        fontFamily = SpartanExtraLight
                    )

                    Text(
                        text = announcement.usuario[0].nomeUsuario,
                        fontSize = 8.sp,
                        fontFamily = SpartanMedium
                    )

                }

                Spacer(modifier = Modifier.height(2.dp))

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



                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
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

        }

    }
}