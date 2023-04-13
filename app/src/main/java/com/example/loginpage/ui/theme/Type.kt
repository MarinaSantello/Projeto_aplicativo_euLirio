package com.example.loginpage.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.loginpage.R

val Spartan = FontFamily(
    Font(R.font.spartan_regular),
    Font(R.font.spartan_light),
    Font(R.font.spartan_extralight),
    Font(R.font.spartan_bold)
)

val SpartanBold = FontFamily(
    Font(R.font.spartan_bold)
)

val SpartanMedium = FontFamily(
    Font(R.font.spartan_medium)
)

val SpartanExtraLight = FontFamily(
    Font(R.font.spartan_extralight)
)

val QuickSand = FontFamily(
    Font(R.font.quicksand_regular)
)


val Roboto = FontFamily(
    Font(R.font.roboto_regular)
)

//MontSerrat Extrabold
val Montserrat = FontFamily(
    Font(R.font.montserrat_extrabold),
    Font(R.font.montserrat_semibold),
)

val MontSerratSemiBold = FontFamily(
    Font(R.font.montserrat_semibold)
)
val MontSerratBold = FontFamily(
    Font(R.font.montserrat_bold)
)


//MontSerrat Regular
val Montserrat2 = FontFamily(
    Font(R.font.montserrat_regular)
)

val Josesans = FontFamily(
    Font(R.font.josefinsans_light)
)

// Set of Material typography styles to start with
@OptIn(ExperimentalTextApi::class)
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
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 2.sp
    ),
    h3 = TextStyle(
        fontFamily = Josesans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 1.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Montserrat2,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 1.sp,
//        lineHeight = 270.em,
//        platformStyle = PlatformTextStyle(
//            includeFontPadding = false
//        ),
//        lineHeightStyle = LineHeightStyle(
//            alignment = LineHeightStyle.Alignment.Center,
//            trim = LineHeightStyle.Trim.FirstLineTop
//        )
    ),

    subtitle2 = TextStyle(
        fontFamily = QuickSand,
        fontSize = 9.sp,
        letterSpacing = 1.sp
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