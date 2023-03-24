package com.example.loginpage.ui.components


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
import com.example.loginpage.models.Genre
import com.example.loginpage.R

@Composable
fun NewGenreCard(genre: Genre, onChecked: (Boolean) -> Unit) {

    var checkState by rememberSaveable() {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .height(30.dp)
            .clickable {
                checkState = !checkState

                onChecked.invoke(checkState)
            },
        backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
        elevation = 0.dp,



    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Checkbox(
                checked = checkState,
                modifier = Modifier
                    .border(
                        1.dp,
                        Color.Black,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .width(16.dp)
                    .height(16.dp)
                ,
                onCheckedChange = {
                    checkState = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Transparent
                ),

                )

            Text(
                text = genre.nome,
                fontWeight = FontWeight.W500,
                fontSize = 10.sp
            )
        }

    }
}