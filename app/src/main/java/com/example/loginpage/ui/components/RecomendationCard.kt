package com.example.loginpage.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Recommendation
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun generateRecommendationCard(
    recomendation: Recommendation,
    navController: NavController
){
    val context = LocalContext.current
    val userID = UserIDrepository(context).getAll()[0].idUser

    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember{
        mutableStateOf(false)
    }

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(recomendation.anuncioID, userID) {
        announcement = it
    }

    Column(
        modifier = Modifier
            .heightIn(min = 200.dp, max = 260.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .heightIn(200.dp)

        ){
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ){
                Card(
                    modifier = Modifier
                        .height(34.dp)
                        .width(34.dp),
                    shape = RoundedCornerShape(100.dp),
                    backgroundColor = Color.Black
                ){}

                Column(
                    modifier = Modifier.padding(start = 5.dp)
                ){
                    Text(
                        text = "Nome Leitor",
                        fontSize = 12.sp,
                        fontFamily = SpartanBold
                    )

                    Text(
                        text = "Nome Leitor",
                        fontSize = 9.sp,
                        fontFamily = SpartanExtraLight
                    )
                }
            }
            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Olha a resenha ai ó",
                fontSize = 10.sp,
                fontFamily = QuickSand,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (announcement != null) CardAnnouncementRecommended(announcement!!)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ){
                //Linha de curtir
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            likeState = !likeState

                        }
                ){
                    Log.i("anuncio get", likeState.toString())
                    //Verificação se o usuário curtiu a publicação
                    if(likeState){
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "icone de curtir",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                        )
                    }

                    else Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "icone de curtir",
                        modifier = Modifier,
                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                    )

                    Text(
                        text = "12",
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
                        .clickable {
                            saveState = !saveState


                        }
                ){

                    //Verificação se o favoritou a publicação
                    if(saveState){
                        Icon(
                            Icons.Outlined.Bookmark,
                            contentDescription = "icone de favoritar",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_yellow_card_background)
                        )
                    }

                    else Icon(
                        Icons.Outlined.BookmarkAdd,
                        contentDescription = "icone de favoritar",
                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_yellow_card_background)
                    )

                    Text(
                        text = "3",
                        fontSize = 10.sp,
                        fontFamily = Montserrat2,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }

}