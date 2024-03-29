package com.example.loginpage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.loginpage.API.genre.CallGenreAPI
import com.example.loginpage.R
import com.example.loginpage.models.Genero
import com.example.loginpage.ui.theme.Montserrat2

@Composable
fun GenerateGenresCards(onChecked: (Boolean, Int) -> Unit){

    var generos by remember {
        mutableStateOf(listOf<Genero>())
    }

    CallGenreAPI.callGetGenre {
        generos = it
    }

    var checkState by remember {
        mutableStateOf(false)
    }

    val rows = (generos.size + 2) / 3

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.eulirio_grey_background))
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Gêneros da história",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.eulirio_purple_text_color_border),
            modifier = Modifier
                .padding(start = 12.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(end = 12.dp)
        ) {

            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    (rowIndex * 3..(rowIndex * 3) + 2).forEachIndexed { colIndex, itemIndex ->
                        if (itemIndex < generos.size) {
                            var checkGenre by rememberSaveable() {
                                mutableStateOf(false)
                            }

                            Row(
                                modifier = Modifier.weight(1f)
                                    .clickable {
                                        checkGenre = !checkGenre

                                        onChecked.invoke(checkGenre, generos[itemIndex].idGenero)
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = checkGenre,
                                    onCheckedChange = {
                                        checkGenre = it

                                        onChecked.invoke(checkGenre, generos[itemIndex].idGenero)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                                        uncheckedColor = colorResource(id = R.color.eulirio_black)
                                    )

                                )
                                Text(
                                    text = generos[itemIndex].nomeGenero.uppercase(),
                                    fontWeight = FontWeight.W500,
                                    fontSize = 12.sp

                                )
                            }

                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}