package com.example.loginpage.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.R
import com.example.loginpage.models.Commit
import com.example.loginpage.models.User
import com.example.loginpage.ui.theme.QuickSand
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanRegular

@Composable
fun CommentCard(
    Comment: Commit,
    navController: NavController
){

    val idUsuario = Comment.userID.toLong();
    var userPicture by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }

    CallAPI.getUser(idUsuario){
        userPicture = it.foto
        userName = it.userName
    }



    //Card de comentarios
    var likeStateComment by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp)
        ){
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.Start
                ){
                    Image(
                        painter = rememberAsyncImagePainter( userPicture ?:"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                        contentScale = ContentScale.Crop,
                        contentDescription = "",
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .border(.5.dp, Color.White, RoundedCornerShape(100.dp))
                    )
                    Column(modifier = Modifier.padding(start = 12.dp), verticalArrangement = Arrangement.Center) {
                        Row(horizontalArrangement = Arrangement.Center){
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                            Icon(
                                Icons.Outlined.StarOutline,
                                contentDescription = "estrela de avaliação",
                                modifier = Modifier.size(20.dp),
                                tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                            )
                        }
                        Text(
                            text = "@${userName}",
                            fontFamily = SpartanRegular,
                            fontSize = 10.sp

                        )

                    }
                }

                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {}
                        .width(40.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,

                    ){
                    Icon(
                        Icons.Outlined.MoreVert, contentDescription = "",
                        modifier = Modifier.size(10.dp),
                        tint = Color.Black
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Column() {
                Text(
                    text = Comment.titulo,
                    fontFamily = SpartanBold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = Comment.resenha,
                    fontFamily = QuickSand,
                    fontSize = 10.sp
                )
            }

        }


        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 6.dp)
        ){
            Card(
                modifier = Modifier
                    .height(20.dp)
                    .width(80.dp)
                ,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(100.dp),
                border = if(likeStateComment){
                    BorderStroke(1.dp, Color.Red)
                }else{
                    BorderStroke(1.dp, Color.Black)
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(likeStateComment){
                        Icon(
                            Icons.Rounded.Favorite,
                            contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Red
                        )
                    }else{
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Black
                        )
                    }


                    Text(
                        text = "182",
                        fontFamily = SpartanRegular,
                        modifier = Modifier.padding(start=6.dp)
                    )

                }

            }

            Text(
                text = "12 Jun. 2022",
                fontFamily = SpartanRegular,
                fontSize = 10.sp
            )


        }


    }
}