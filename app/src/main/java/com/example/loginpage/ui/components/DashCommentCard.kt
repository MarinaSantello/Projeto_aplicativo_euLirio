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
import androidx.compose.ui.Alignment
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
    avaliacao: Double,
    charts: List<Double>
) {
    val filledStars = floor(avaliacao).toInt()
    val unfilledStars = (5 - ceil(avaliacao)).toInt()
    val halfStar = !(avaliacao.rem(1).equals(0.0))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Sistema de avaliação
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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

        }

        Text(
            text = "$avaliacao de 5,0",
            fontFamily = SpartanBold,
            fontSize = 18.sp
        )

    }

    Spacer(Modifier.height(16.dp))

    for (i in charts.indices) {
        BarChart(star = 5 - i, percent = charts[i])

        Spacer(Modifier.height(8.dp))
    }
}