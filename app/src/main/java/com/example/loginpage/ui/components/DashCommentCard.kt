package com.example.loginpage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.R
import com.example.loginpage.ui.components.Charts.BarChart
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanExtraLight
import com.example.loginpage.ui.theme.SpartanMedium
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun DashCommentCard(
) {
    val avaliacao = 3.2
    val charts = listOf(12.5, 75.4, 0.5, 1.6, 5.0)

    val filledStars = floor(avaliacao).toInt()
    val unfilledStars = (5 - ceil(avaliacao)).toInt()
    val halfStar = !(avaliacao.rem(1).equals(0.0))

    Card(
        modifier = Modifier.wrapContentSize()
    ) {

    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        //Sistema de avaliação
        Row() {
            repeat(filledStars) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp),
                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                )
            }

            if (halfStar) {
                Icon(
                    imageVector = Icons.Outlined.StarHalf,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp),
                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                )
            }

            repeat(unfilledStars) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp),
                    tint = colorResource(R.color.eulirio_purple_text_color_border)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(text = "(${avaliacao})".replace('.', ','))

        }

        Text(
            text = "ytfh de 5,0",
            modifier = Modifier.padding(start= 16.dp,  top = 14.dp),
            fontFamily = SpartanBold,
            fontSize = 20.sp
        )

    }

    Spacer(Modifier.height(20.dp))

    for (i in charts.indices) {
        BarChart(star = 3, percent = charts[i])

        Spacer(Modifier.height(10.dp))
    }
}