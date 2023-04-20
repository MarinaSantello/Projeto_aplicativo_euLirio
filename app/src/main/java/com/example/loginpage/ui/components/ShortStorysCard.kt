package com.example.loginpage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
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
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.favorite.CallFavoriteAPI
import com.example.loginpage.API.like.CallLikeAPI
import com.example.loginpage.API.visualization.CallVisualizationAPI
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.*
import com.example.loginpage.ui.theme.*


@Composable
fun ShortStorysCard(
    shortStory: ShortStoryGet,
    userID: Int
) {

    var likeState by remember {
        mutableStateOf(false)
    }

    var saveState by remember {
        mutableStateOf(false)
    }

    var viewState by remember {
        mutableStateOf(false)
    }

    var viewComents by remember {
        mutableStateOf(false)
    }


    var quantidadeLikesState by remember {
        mutableStateOf("")
    }

    CallLikeAPI.countShortStoriesLikes(shortStory.id!!){
        quantidadeLikesState = it.qtdeCurtidas
    }

    Card(
        modifier = Modifier
            .height(204.dp)
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
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
                    text = shortStory.titulo,
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
                        text = shortStory.usuario[0].nomeUsuario,
                        fontSize = 10.sp,
                        fontFamily = SpartanMedium
                    )

                }
            }

            Row() {
                //Imagem da capa do livro
                Image(
                    painter = rememberAsyncImagePainter(shortStory.capa),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "estrela de avaliação",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                        )
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "estrela de avaliação",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                        )
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "estrela de avaliação",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                        )
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "estrela de avaliação",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                        )
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "estrela de avaliação",
                            tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                        )

                    }

                    var generos by remember {
                        mutableStateOf(listOf<Generos>())
                    }

//                    generos = shortStory.generos

                    LazyRow() {
                        items(items = generos) {
                            Card(
                                modifier = Modifier
                                    .height(14.dp)
                                    .padding(start = 4.dp, end = 4.dp),
                                backgroundColor = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border),
                                shape = RoundedCornerShape(100.dp),
                            ) {
                                Text(
                                    text = it.nome.uppercase(),
                                    fontSize = 10.sp,
                                    fontFamily = MontSerratSemiBold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp),
                                    color = Color.White
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = shortStory.sinopse,
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "balão de comentario",
                                modifier = Modifier.padding(end = 2.dp)
                            )

                            Text(
                                text = "28",
                                fontSize = 10.sp,
                                fontFamily = Montserrat2,
                                fontWeight = FontWeight.W500,
                            )
                        }

                        Row() {
                            //Linha de curtir
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {
                                        likeState = !likeState
                                        if (likeState) {


                                            var shortStorieLike = LikeShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallLikeAPI.likeShortStorie(shortStorieLike)

                                        } else {
                                            CallLikeAPI.countShortStoriesLikes(shortStory.id!!){
                                                quantidadeLikesState = it.qtdeCurtidas
                                            }


                                            var shortStorieUnLike = LikeShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallLikeAPI.dislikeShortStorie(shortStorieUnLike)
                                        }
                                    }
                            ) {

                                //Verificação se o usuário curtiu a publicação
                                if (likeState) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "icone de curtir",
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_like)
                                    )
                                } else Icon(
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable {
                                        saveState = !saveState

                                        if (!saveState) {
                                            val favoriteShortStorieUnCheck = FavoriteShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.unfavoriteShortStorie(
                                                favoriteShortStorieUnCheck
                                            )
                                        } else {
                                            val favoriteShortStorieCheck = FavoriteShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallFavoriteAPI.favoriteShortStorie(
                                                favoriteShortStorieCheck
                                            )
                                        }
                                    }
                            ) {

                                //Verificação se o favoritou a publicação
                                if (saveState) {
                                    Icon(
                                        Icons.Outlined.Bookmark,
                                        contentDescription = "icone de favoritar",
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_yellow_card_background)
                                    )
                                } else Icon(
                                    Icons.Outlined.BookmarkAdd,
                                    contentDescription = "icone de favoritar",
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_yellow_card_background)
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
                                    .clickable {
                                        viewState = !viewState

                                        if (!viewState) {
                                            val unViewShortStorie = VisualizationShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.unViewShortStorie(unViewShortStorie)
                                        } else {
                                            val viewShortStorie = VisualizationShortStorie(
                                                idHistoriaCurta = shortStory.id,
                                                idUsuario = userID
                                            )
                                            CallVisualizationAPI.viewShortStorie(viewShortStorie)
                                        }
                                    }
                            ) {

                                //Verificação se o usuário visualizou a publicação
                                if (viewState) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "icone de visualizacão",
                                        tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                                    )
                                } else Icon(
                                    Icons.Outlined.CheckCircle,
                                    contentDescription = "icone de visualizacão",
                                    tint = colorResource(id = com.example.loginpage.R.color.eulirio_purple_text_color_border)
                                )

                                Text(
                                    text = "44",
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