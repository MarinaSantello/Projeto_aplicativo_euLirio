package com.example.loginpage.ui.components

import android.annotation.SuppressLint
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
import com.example.loginpage.R

@Composable
fun GenreCard(genre: Int) {

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
                colorResource(id = R.color.eulirio_purple),
                RoundedCornerShape(30.dp)
            )
            .clickable {
//                contacts = contacts.mapIndexed { j, item ->
//                    if (i == j){
//                        item.copy(active = !item.active)
//                    } else item
//                }
            },
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Genero $genre",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.eulirio_purple)
            )
            if (checkState) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.3f)
                            .background(colorResource(id = R.color.eulirio_purple))
                    ) {
                        Checkbox(
                            checked = checkState,
                            onCheckedChange = {
                                checkState = it
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(id = R.color.eulirio_purple),
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