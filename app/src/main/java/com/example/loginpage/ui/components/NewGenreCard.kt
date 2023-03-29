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
import com.example.loginpage.models.Genero
import com.example.loginpage.ui.theme.Montserrat2

@Composable
fun NewGenreCard(genre: Genero, onChecked: (Boolean) -> Unit) {

    var checkState by rememberSaveable() {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .widthIn(80.dp)
            .height(30.dp),
        backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
        elevation = 0.dp
    ) {
        Row(
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
                    .padding(start = 2.dp),
                onCheckedChange = {
                    checkState = it

                    onChecked.invoke(checkState)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.eulirio_black),
                    uncheckedColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = genre.nomeGenero.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat2,
                color = colorResource(id = R.color.eulirio_black)
            )
        }

    }
}