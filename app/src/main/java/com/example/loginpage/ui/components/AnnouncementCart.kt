package com.example.loginpage.ui.components

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.cart.CallCartAPI
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.CartData
import com.example.loginpage.ui.theme.MontSerratSemiBold
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanMedium

@Composable
fun AnnouncementCart(
    announcement: CartData,
    userID: Int,
    navController: NavController,
    bottomBarLength: Dp,
    type: Int,
    context: Context
) {

    val priceVerify = announcement.preco.toString().split('.')
    var price = announcement.preco.toString()

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
                .padding(20.dp, 8.dp),
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
                    val files = listOf("PDF", "ePUB", if (announcement.mobi != "null") "MOBI" else "",)

                    Text(text = announcement.titulo,
                        fontFamily = SpartanBold,
                        modifier = Modifier.padding(top = 18.dp, start = 12.dp, bottom = 4.dp)
                    )

                    LazyRow() {
                        items(files) {
                            if (it.isNotEmpty()) Card(
                                modifier = Modifier
                                    .height(16.dp)
                                    .padding(start = 12.dp)
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

                    if (priceVerify[1].isEmpty())
                        price = "${priceVerify[0]}.00"
                    else if (priceVerify[1].length == 1)
                        price = "${announcement.preco}0"

                    Text(
                        text = "R$ ${price.replace('.', ',')}",
                        fontFamily = SpartanMedium,
                        modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 88.dp))

                    if (type == 0) Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "icone para excluir o livro do carrinho",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                CallCartAPI.deleteItemCart(announcement.anuncioID, userID) {
                                    if (it == 200) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Livro excluído do carrinho.",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()

                                        navController.navigate("${Routes.ShoppingCart.name}/$userID")
                                    }
                                }
                            }
                    )
                }
            }
        }
    }
}