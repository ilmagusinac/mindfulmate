package com.example.mindfulmate.presentation.navigation.bottom_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.navigation.Screen
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.theme.SuperLightGrey

@Composable
fun BottomBarNavigationComponent(
    items: List<BottomBarNavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.rounded_corners),
            topEnd = dimensionResource(id = R.dimen.rounded_corners)
        ),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.border_light),
            color = LightGrey
        )
    ) {
        NavigationBar(
            containerColor = Color.Transparent
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedItemIndex == index

                NavigationBarItem(
                    modifier = Modifier.wrapContentSize(),
                    selected = selected,
                    onClick = { onItemSelected(index) },
                    label = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = item.titleId),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (selected) Blue else Grey,
                                    fontSize = 12.sp,
                                    fontWeight =  if (selected) FontWeight.W400 else FontWeight.W300
                                )
                            )
                        }
                    },
                    icon = {
                        if (item.route == Screen.ChatHome.route && item.unreadCount!! > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(containerColor = SuperLightGrey) {
                                        Text(
                                            text = if (item.unreadCount > 9) "9+" else item.unreadCount.toString(),
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = item.iconRes),
                                    contentDescription = stringResource(id = item.titleId),
                                    tint = if (selected) Blue else Grey
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = stringResource(id = item.titleId),
                                tint = if (selected) Blue else Grey
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Grey,
                        unselectedIconColor = Grey,
                        selectedTextColor = if (selected) Grey else Blue,
                        unselectedTextColor = Grey,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarNavigationComponentPreview() {
    MindfulMateTheme {
        val items = listOf(
            BottomBarNavigationItem(
                titleId = R.string.home,
                iconRes = R.drawable.ic_home,
                route = "home",
            ),
            BottomBarNavigationItem(
                titleId = R.string.community,
                iconRes = R.drawable.ic_community,
                route = "community"
            ),
            BottomBarNavigationItem(
                titleId = R.string.resources,
                iconRes = R.drawable.ic_chat,
                route = Screen.ChatHome.route,
                unreadCount = 5
            ),
            BottomBarNavigationItem(
                titleId = R.string.profile,
                iconRes = R.drawable.ic_profile,
                route = "profile"
            )
        )
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        BottomBarNavigationComponent(
            items = items,
            selectedItemIndex = selectedItemIndex,
            onItemSelected = { index -> selectedItemIndex = index }
        )
    }
}
