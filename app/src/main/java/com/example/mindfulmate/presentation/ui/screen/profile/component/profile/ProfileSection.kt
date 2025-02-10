package com.example.mindfulmate.presentation.ui.screen.profile.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage

@Composable
fun ProfileSection(
    imageUrl: String,
    firstName: String,
    lastName: String,
    username: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        MindfulMateProfileImage(
            imageUrl = imageUrl,
            placeholderRes = R.drawable.ic_profile,
            backgroundColor = DuskyBlue,
            size = dimensionResource(id = R.dimen.icon_xxlarge),
            tint = DuskyWhite
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        Text(
            text = "$firstName $lastName",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = "@$username",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
private fun ProfileSectionPreview() {
    MindfulMateTheme {
        ProfileSection(
            imageUrl = "https://hips.hearstapps.com/hmg-prod/images/dahlia-1508785047.jpg?crop=1.00xw:0.669xh;0,0.0136xh&resize=980:*",
            firstName = "Ilma",
            lastName = "Gusinac",
            username = "username"
        )
    }
}
