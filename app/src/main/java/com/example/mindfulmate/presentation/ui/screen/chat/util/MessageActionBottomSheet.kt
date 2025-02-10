package com.example.mindfulmate.presentation.ui.screen.chat.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageActionBottomSheet(
    message: Message,
    currentUser: String?,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isCurrentUserMessage = message.senderId == currentUser

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        if (isCurrentUserMessage) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DuskyGrey)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        tint = Grey,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(id = R.string.edit_message),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Grey,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_default))
                            .clickable {
                                scope.launch { sheetState.hide() }
                                onEdit()
                            }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Grey,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(id = R.string.delete_message),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Grey,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_default))
                            .clickable {
                                scope.launch { sheetState.hide() }
                                onDelete()
                            }
                    )
                }
            }
        }else {
            Text(
                text = "No actions available",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun MessageActionBottomSheetPreview() {
    MindfulMateTheme {
        MessageActionBottomSheet(
            onDismiss = {},
            onEdit = {},
            onDelete = {},
            message = Message(

            ),
            currentUser = TODO()
        )
    }
}
