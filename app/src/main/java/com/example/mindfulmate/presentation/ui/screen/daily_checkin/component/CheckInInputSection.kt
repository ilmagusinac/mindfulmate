package com.example.mindfulmate.presentation.ui.screen.daily_checkin.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.SuperLightGrey
import com.example.mindfulmate.presentation.ui.screen.home.component.BackgroundHeader

@Composable
fun CheckInInputSection(
    inputClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_large)))
    ) {
        BackgroundHeader(
            heightIn = dimensionResource(id = R.dimen.height_defaultx)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(id = R.dimen.spacing_xxmedium),
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_default))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_happy),
                contentDescription = null,
                tint = if (selectedMood == "happy") SuperLightGrey else DuskyWhite,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_large))
                    .clickable {
                        selectedMood = "happy"
                        inputClick("happy")
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_neutral),
                contentDescription = null,
                tint = if (selectedMood == "neutral") SuperLightGrey else DuskyWhite,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_large))
                    .clickable {
                        selectedMood = "neutral"
                        inputClick("neutral")
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_sad),
                contentDescription = null,
                tint = if (selectedMood == "sad") SuperLightGrey else DuskyWhite,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_large))
                    .clickable {
                        selectedMood = "sad"
                        inputClick("sad")
                    }
            )
        }
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF3D3D3D
)
@Composable
private fun CheckInInputSectionPreview() {
    MindfulMateTheme {
        CheckInInputSection(
            inputClick = {}
        )
    }
}