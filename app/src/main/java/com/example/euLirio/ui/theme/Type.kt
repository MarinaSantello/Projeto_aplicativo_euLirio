package com.example.euLirio.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.euLirio.R

val Spartan = FontFamily(
    Font(R.font.spartan_regular),
    Font(R.font.spartan_light),
    Font(R.font.spartan_extralight),
    Font(R.font.spartan_bold)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Spartan,
        fontWeight = FontWeight.W100,
        fontSize = 10.sp
    ),
    h1 = TextStyle(
        fontFamily = Spartan,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)