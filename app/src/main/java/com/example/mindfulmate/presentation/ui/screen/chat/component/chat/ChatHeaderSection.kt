package com.example.mindfulmate.presentation.ui.screen.chat.component.chat

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopup
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopupDialog
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage
import com.example.mindfulmate.presentation.ui.component.util.PopupMenuItem
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential.IconPlacement
import com.example.mindfulmate.presentation.util.DialogButtonConfig

@Composable
fun ChatHeaderSection(
    username: String,
    onBackButtonClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null
) {
    var isPopupExpanded by remember { mutableStateOf(false) }
    var isPopupDialogExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .background(DuskyGrey)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_mediumx),
                vertical = dimensionResource(id = R.dimen.padding_default)
            )
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = Grey,
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.width_medium),
                    height = dimensionResource(id = R.dimen.height_medium)
                )
                .clickable { onBackButtonClick() }
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xmedium)))
        /*IconPlacement(
            placeholderRes = placeholderRes,
            backgroundColor = DuskyWhite
        )*/
        MindfulMateProfileImage(
            imageUrl = imageUrl,
            size = dimensionResource(id = R.dimen.icon_large),
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
        Text(
            text = "@$username",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_dots),
            contentDescription = null,
            tint = Grey,
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.width_medium),
                    height = dimensionResource(id = R.dimen.height_medium)
                )
                .clickable { isPopupExpanded = !isPopupExpanded }
        )
    }
    MindfulMatePopup(
        expanded = isPopupExpanded,
        onDismissRequest = { isPopupExpanded = false },
        items = listOf(
            PopupMenuItem(
                label = stringResource(R.string.delete_conversation),
                icon = painterResource(id = R.drawable.ic_trash_bin),
                onClick = {
                    onDeleteChatClick()
                    isPopupDialogExpanded = true
                    isPopupExpanded = false
                }
            )
        )
    )/*
    if (isPopupDialogExpanded) {
        MindfulMatePopupDialog(
            onDismissRequest = { isPopupDialogExpanded = false },
            dialogTitle = stringResource(id = R.string.delete_convo_title),
            dialogText = stringResource(id = R.string.delete_convo_label),
            buttons = listOf(
                DialogButtonConfig(
                    text = stringResource(id = R.string.delete),
                    onConfirmationClick = onDeleteChatClick,
                    isPrimary = true
                ),
                DialogButtonConfig(
                    text = stringResource(id = R.string.cancel),
                    onConfirmationClick = { isPopupDialogExpanded = false },
                    isPrimary = false
                )
            ),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_default))
        )
    }*/
}

@Preview
@Composable
private fun ChatHeaderSectionPreview() {
    MindfulMateTheme {
        ChatHeaderSection(
            username = "username",
            onBackButtonClick = {},
            onDeleteChatClick = {}
        )
    }
}
