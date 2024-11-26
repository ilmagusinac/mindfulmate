package com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
fun EditProfileSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        MindfulMateProfileImage(
            imageUrl = null,
            placeholderRes = R.drawable.ic_profile,
            backgroundColor = DuskyBlue,
            size = dimensionResource(id = R.dimen.icon_xlarge),
            tint = DuskyWhite
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Grey)) {
                    append(stringResource(id = R.string.upload))
                }
                append(stringResource(id = R.string.new_avatar_or))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Grey)) {
                    append(stringResource(id = R.string.remove))
                }
                append(stringResource(id = R.string.existing_one))
            },
            style = MaterialTheme.typography.titleMedium.copy(
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
        EditProfileSection()
    }
}
