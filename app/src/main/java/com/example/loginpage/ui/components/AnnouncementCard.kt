package com.example.loginpage.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.*
import com.example.loginpage.ui.theme.*
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun AnnouncementCard(
    announcement: AnnouncementGet,
    userID: Int,
    navController: NavController,
    type: Int,
    fav: Boolean,
    read: Boolean
) {

    var likeState by remember {
        mutableStateOf(announcement.curtido)
    }

    var saveState by remember{
        mutableStateOf(announcement.favorito)
    }

    var viewState by remember{
        mutableStateOf(announcement.lido)
    }

    var quantidadeLikesState by remember{
        mutableStateOf("")
    }

    var quantidadeFavoritosState by remember{
        mutableStateOf("")
    }

    var quantidadeViewsState by remember{
        mutableStateOf("")
    }


    CallLikeAPI.countAnnouncementLikes(announcement.id!!){
        quantidadeLikesState = it.qtdeCurtidas
    }

    CallFavoriteAPI.countFavoritesAnnouncement(announcement.id!!){
        quantidadeFavoritosState = it.qtdeFavoritos
    }

    CallVisualizationAPI.countViewAnnouncement(announcement.id!!){
        quantidadeViewsState = it.qtdeLidos
    }

    CallAnnouncementAPI.getAnnouncement(announcement.id!!, userID) {
        likeState = it.curtido
        saveState = it.favorito
        viewState = it.lido
    }
    val priceVerify = announcement.preco.toString().split('.')
    var price = announcement.preco.toString()

    val filledStars = floor(announcement.avaliacao).toInt()
    val unfilledStars = (5 - ceil(announcement.avaliacao)).toInt()
    val halfStar = !(announcement.avaliacao.rem(1).equals(0.0))

    Card(
        modifier = Modifier
            .height(204.dp)
            .fillMaxWidth()
            .padding(bottom = 2.dp)
            .clickable {
                if (type == 1) navController.navigate("${Routes.Ebook.name}/${announcement.id}")

                if (type == 2) navController.navigate("${Routes.EditEbook.name}/${announcement.id}")

            },
        backgroundColor = Color.White,
        elevation = 0.dp
    ){
        //Criação dos cards
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp)
        ) {

            Column(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = announcement.titulo,
                    fontSize = 20.sp,
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
                        text = announcement.usuario[0].nomeUsuario,
                        fontSize = 10.sp,
                        fontFamily = SpartanMedium
                    )

                }
            }

            Row() {
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

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    //Sistema de avaliação
                    if (announcement.avaliacao > 0) Row (Modifier.padding(bottom = 8.dp)) {
                        repeat(filledStars) {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = colorResource(com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                        }

                        if (halfStar) {
                            Icon(
                                imageVector = Icons.Outlined.StarHalf,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = colorResource(com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                        }

                        repeat(unfilledStars) {
                            Icon(
                                imageVector = Icons.Outlined.StarOutline,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = colorResource(com.example.loginpage.R.color.eulirio_purple_text_color_border)
                            )
                        }
                    }

                    var generos by remember {
                        mutableStateOf(listOf<Generos>())
                    }

                    LazyRow() {
                        if (!(announcement.generos.isNullOrEmpty())) items(items = announcement.generos) {
                            Card(
                                modifier = Modifier
                                    .height(14.dp)
                                    .padding(start = 4.dp, end = 4.dp)
                                ,
                                backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border),
                                shape = RoundedCornerShape(100.dp),
                            ) {
                                Text(
                                    text = it.nome.uppercase(),
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = announcement.sinopse,
                        modifier = Modifier.height(60.dp),
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
                        if (priceVerify[1].isEmpty())
                            price = "${priceVerify[0]}.00"
                        else if (priceVerify[1].length == 1)
                            price = "${announcement.preco}0"

                        Text(
                            text = "R$ ${price.replace('.', ',')}",
                            fontSize = 16.sp,
                            fontFamily = Roboto,
                            fontWeight = FontWeight.Bold
                        )

                        Row() {
                            //Linha de curtir
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {
                                        likeState = !likeState

                                        if (!likeState) {
//                                            likeState = !likeState
                                            val announcementDislike = LikeAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )

                                            CallLikeAPI.dislikeAnnouncement(announcementDislike)
//                                            likeState = false

                                            var newUnlikeConvert = quantidadeLikesState.toInt() - 1
                                            quantidadeLikesState = newUnlikeConvert.toString()
                                        } else {

                                            val announcementLike = LikeAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )

                                            CallLikeAPI.likeAnnouncement(announcementLike)

                                            var newlikeConvert = quantidadeLikesState.toInt() + 1
                                            quantidadeLikesState = newlikeConvert.toString()
                                        }


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
                                    text = quantidadeLikesState,
                                    fontSize = 10.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.W500,
                                )
                            }

                            //Linha de favoritar
                            if (fav) Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {
                                        saveState = !saveState

                                        if (!saveState) {
                                            val announcementUnFavorite = FavoriteAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.unfavoriteAnnouncement(
                                                announcementUnFavorite
                                            )

                                            var newUnSaveConvert = quantidadeFavoritosState.toInt() - 1
                                            quantidadeFavoritosState = newUnSaveConvert.toString()

                                        } else {

                                            val announcementFavorite = FavoriteAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.favoriteAnnouncement(
                                                announcementFavorite
                                            )

                                            var newSaveConvert = quantidadeFavoritosState.toInt() + 1
                                            quantidadeFavoritosState = newSaveConvert.toString()

                                        }
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
                                    text = quantidadeFavoritosState,
                                    fontSize = 10.sp,
                                    fontFamily = Montserrat2,
                                    fontWeight = FontWeight.W500
                                )
                            }

                            //Linha de visualização
                            if (read) Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        viewState = !viewState

                                        if(!viewState){
                                            val unViewAnnouncement = VisualizationAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.unViewAnnouncement(unViewAnnouncement)

                                            var newUnViewConvert = quantidadeViewsState.toInt() - 1
                                            quantidadeViewsState = newUnViewConvert.toString()

                                        }else{
                                            val viewAnnouncement = VisualizationAnnouncement(
                                                idAnuncio = announcement.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.viewAnnouncement(viewAnnouncement)

                                            var newViewConvert = quantidadeViewsState.toInt() + 1
                                            quantidadeViewsState = newViewConvert.toString()
                                        }

                                    }
                            ){

                                //Verificação se o usuário visualizou a publicação

                                if(viewState){
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de visualizacão",
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                                    )
                                }

                                else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de visualizacão",
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                                )

                                Text(
                                    text = quantidadeViewsState ?: "0",
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