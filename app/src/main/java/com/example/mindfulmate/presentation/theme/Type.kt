package com.example.mindfulmate.presentation.theme

import com.example.mindfulmate.R
import androidx.compose.ui.text.font.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DMSans = FontFamily(
    Font(R.font.dmsans_black, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.dmsans_blackitalic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.dmsans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.dmsans_bolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.dmsans_extrabold, FontWeight.W900, FontStyle.Normal),
    Font(R.font.dmsans_extrabolditalic, FontWeight.W900, FontStyle.Italic),

    Font(R.font.dmsans_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.dmsans_thinitalic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.dmsans_light, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.dmsans_lightitalic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(R.font.dmsans_medium, FontWeight.W300, FontStyle.Normal),
    Font(R.font.dmsans_mediumitalic, FontWeight.W300, FontStyle.Italic),

    Font(R.font.dmsans_regular, FontWeight.Normal, FontStyle.Normal),

    Font(R.font.dmsans_semibold, FontWeight.W500, FontStyle.Normal),
    Font(R.font.dmsans_semibolditalic, FontWeight.W500, FontStyle.Italic)

)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    )
)
