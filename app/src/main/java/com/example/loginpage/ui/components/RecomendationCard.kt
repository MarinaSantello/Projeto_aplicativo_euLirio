package com.example.loginpage.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.Recommendation
import com.example.loginpage.models.User
import com.example.loginpage.models.likeRecommendation
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun generateRecommendationCard(
    recomendation: Recommendation,
    navController: NavController,
    userID: Int
){
    val context = LocalContext.current
    val userID = UserIDrepository(context).getAll()[0].idUser

    var likeState by remember {
        mutableStateOf(recomendation.curtido)
    }

    var likeCounter by remember{
        mutableStateOf(recomendation.curtidas!!.qtdeCurtidas.toString())
    }

    var saveState by remember{
        mutableStateOf(recomendation.favorito)
    }

    var saveCounter by remember{
        mutableStateOf(recomendation.favoritos!!.qtdeFavoritos.toString())
    }

    var announcement by remember {
        mutableStateOf<AnnouncementGet?>(null)
    }

    CallAnnouncementAPI.getAnnouncement(recomendation.anuncioID, userID) {
        announcement = it
    }

    var nameRecommendationUser by remember{
        mutableStateOf("")
    }

    var recommendationUserName by remember{
        mutableStateOf("")
    }

    var recommendationUserPicture by remember{
        mutableStateOf("")
    }

    CallAPI.getUser(recomendation.userID.toLong()!!){
        nameRecommendationUser = it.nome
        recommendationUserName = it.userName
        recommendationUserPicture = it.foto
    }

    var visibilitySpoiler by remember {
        mutableStateOf(recomendation.spoiler)
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
                Image(
                    painter = rememberAsyncImagePainter(recommendationUserPicture),
                    contentDescription = "foto da pessoa",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                    )

                Column(
                    modifier = Modifier.padding(start = 5.dp)
                ){
                    Text(
                        text = nameRecommendationUser,
                        fontSize = 12.sp,
                        fontFamily = SpartanBold
                    )

                    Text(
                        text = recommendationUserName,
                        fontSize = 9.sp,
                        fontFamily = SpartanExtraLight
                    )
                }
            }
            Spacer(modifier = Modifier.height(7.dp))

            if(visibilitySpoiler == "1") Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { visibilitySpoiler = "0" },
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Contem Spoiler",
                        fontFamily = SpartanBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Icon(
                        imageVector = Icons.Outlined.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp),
                        tint = colorResource(com.example.loginpage.R.color.eulirio_purple_text_color_border)
                    )


                }
            }else{
                Text(
                    text = recomendation.conteudo,
                    fontSize = 10.sp,
                    fontFamily = QuickSand,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (announcement != null) CardAnnouncementRecommended(announcement!!, navController)

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
                            likeState = !likeState!!

                            if(likeState == true){

                                var likerRecommendation = likeRecommendation(
                                    idUsuario = userID,
                                    idRecomendacao = recomendation.id
                                )
                                CallLikeAPI.likeRecommendation(likerRecommendation)

                                var addLikeCounter = likeCounter.toInt() + 1
                                likeCounter = addLikeCounter.toString()

                            }else{
                                CallLikeAPI.dislikeRecommendation(recomendation.id!!, userID)

                                var removeLikeCounter = likeCounter.toInt() - 1
                                likeCounter = removeLikeCounter.toString()
                            }

                        }
                ){
                    Log.i("anuncio get", likeState.toString())
                    //Verificação se o usuário curtiu a publicação
                    if(likeState == true){
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
                        text = likeCounter,
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
                            saveState = !saveState!!

                            if(saveState == true){
                                var favoriterRecommendation = likeRecommendation(
                                    idUsuario = userID,
                                    idRecomendacao = recomendation.id
                                )

                                CallFavoriteAPI.favoriteRecommendation(favoriterRecommendation)

                                var addSaveCounter = saveCounter.toInt() + 1
                                saveCounter = addSaveCounter.toString()
                            }else{
                                CallFavoriteAPI.unFavoriteRecommendation(recomendation.id!!, userID)

                                var removeSaveCounter = saveCounter.toInt() - 1
                                saveCounter = removeSaveCounter.toString()
                            }


                        }
                ){

                    //Verificação se o favoritou a publicação
                    if(saveState == true){
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
                        text = saveCounter,
                        fontSize = 10.sp,
                        fontFamily = Montserrat2,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }

}