package com.example.loginpage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.CartData
import com.example.loginpage.ui.theme.MontSerratSemiBold

@Composable
fun AnnouncementCart(
    announcement: CartData,
    navController: NavController,
    type: Int
) {
    Card(
        modifier = Modifier
            .height(204.dp)
            .fillMaxWidth()
            .padding(bottom = 2.dp)
            .clickable {
                navController.navigate("${Routes.Ebook.name}/${announcement.anuncioID}")
            },
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Imagem da capa do livro
            Image(
                painter = rememberAsyncImagePainter(announcement.capa),
                contentDescription = "",
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.fillMaxWidth()) {
                    val files = listOf("PDF", "ePUB", if (!(announcement.mobi.isNullOrEmpty())) "MOBI" else "")

                    Text(text = announcement.titulo)

                    LazyRow() {
                        items(files) {
                            if (it.isNotEmpty()) Card(
                                modifier = Modifier
                                    .height(14.dp)
                                    .padding(start = 4.dp, end = 4.dp)
                                ,
                                backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border),
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

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "${announcement.preco}")

                    if (type == 1) Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "icone para excluir o livro do carrinho"
                    )
                }
            }
        }
    }
}