package com.example.mindfulmate.presentation.ui.screen.settings.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.SuperLightGrey
import com.example.mindfulmate.presentation.ui.component.MindfulMateIconPlacement
import com.example.mindfulmate.presentation.ui.screen.settings.util.ContentRowType

@Composable
fun ContentRow(
    title: String,
    label: String,
    onRowIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
    tint: Color = DuskyBlue,
    rowType: ContentRowType = ContentRowType.ICON,
    switchState: Boolean = false,
    //onSwitchChange: ((Boolean) -> Unit)? = null
) {
    //var checked by remember { mutableStateOf(switchState) }

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_xsmall)
            )
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MindfulMateIconPlacement(
                placeholderRes = placeholderRes,
                tint = tint
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 12.sp
                    )
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            when (rowType) {
                ContentRowType.ICON -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.icon_small))
                            .clickable { onRowIconClick() },
                        tint = LightGrey
                    )
                }

                ContentRowType.SWITCH -> {
                    Switch(
                        checked = switchState,
                        onCheckedChange = { onRowIconClick() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DuskyBlue,
                            uncheckedThumbColor = LightGrey,
                            uncheckedTrackColor = SuperLightGrey,
                            uncheckedBorderColor = SuperLightGrey,
                            checkedTrackColor = Blue,
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        ContentRow(
            title = "My Account",
            label = "Make changes to your account",
            onRowIconClick = {}
        )
    }
}
