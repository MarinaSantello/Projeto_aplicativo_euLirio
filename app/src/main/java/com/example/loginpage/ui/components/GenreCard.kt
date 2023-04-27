package com.example.loginpage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.euLirio.R
import com.example.loginpage.R
import com.example.loginpage.models.Genero

@Composable
fun GenreCard(
    genre: Genero,
    color: Color,
    check: Boolean,
    onChecked: (Boolean) -> Unit
) {

    var checkState by rememberSaveable() {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(.4f)
            .height(48.dp)
            .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
            .border(
                1.5.dp,
                color,
                RoundedCornerShape(30.dp)
            )
            .clickable {
//                genres = genres.mapIndexed { j, item ->
//                    if (i == j){
//                        item.copy(active = !item.active)
//                    } else item
//                }
                checkState = !checkState

                onChecked.invoke(checkState)
            },
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = genre.nomeGenero.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            if (checkState && check) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.3f)
                            .background(color)
                    ) {
                        Checkbox(
                            checked = checkState,
                            onCheckedChange = {
                                checkState = it
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = color,
                                uncheckedColor = Color.Transparent
                            ),
                        )
//                        Icon(
//                            Icons.Rounded.Check,
//                            contentDescription = "Icone de check",
//                            modifier = Modifier
//                                .fillMaxHeight(.5f)
//                                .align(Alignment.Center),
//                            tint = Color.White
//                        )
                    }
                }
            }
        }
    }
}