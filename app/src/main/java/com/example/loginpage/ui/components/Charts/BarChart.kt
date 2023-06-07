package com.example.loginpage.ui.components.Charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.loginpage.R
import com.example.loginpage.ui.theme.SpartanExtraLight

@Composable
fun BarChart(
    star: Int,
    percent: Double
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "$star" + if (star > 1) " estrelas" else " estrela",
            modifier = Modifier
                .width(62.dp),
            fontFamily = SpartanExtraLight
        )

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(16.dp)
                .padding(start = 8.dp)
                .background(
                    colorResource(id = R.color.eulirio_grey_background),
                    shape = RoundedCornerShape(6.dp)

                )

        ) {
            Box(
                modifier = Modifier
                    .width(percent.dp * 2)
                    .fillMaxHeight()
                    .background(
                        colorResource(id = R.color.eulirio_purple_text_color_border),
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {

            }
        }

        Text(text = "$percent%",
            modifier = Modifier
                .width(52.dp)
        )

    }
}