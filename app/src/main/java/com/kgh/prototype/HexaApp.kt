package com.kgh.prototype

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kgh.prototype.ui.navigation.HexaNavHost
import com.kgh.prototype.ui.openSettingIntent

@Composable
fun HexaApp(
    navController: NavHostController = rememberNavController(),
    activity: Activity,
) {
    HexaNavHost(
        navController = navController,
        activity = activity,
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun HexaTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    context: Context = LocalContext.current,
) {

    var dropDownMenuExpanded by remember {
        mutableStateOf(false)
    }

    if (canNavigateBack) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            },
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_ios_new_fill1_wght300_grad0_opsz48),
                        contentDescription = "뒤로 가기 버튼",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    // show the drop down menu
                    dropDownMenuExpanded = true
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More Vert",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                // drop down menu
                DropdownMenu(
                    expanded = dropDownMenuExpanded,
                    onDismissRequest = {
                        dropDownMenuExpanded = false
                    },
                    // play around with these values
                    // to position the menu properly
                    offset = DpOffset(x = 10.dp, y = (-60).dp)
                ) {
                    // this is a column scope
                    // items are added vertically

                    DropdownMenuItem(modifier = Modifier.padding(end = 30.dp), onClick = {
                        // 드랍다운 메뉴 클릭
                    }) {
                        Text(
                            text = "빈 링크 1",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                        )
                    }

                    DropdownMenuItem(modifier = Modifier.padding(end = 30.dp), onClick = {
                        openSettingIntent(context)
                    }) {
                        Text(
                            text = "설정",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                        )
                    }

                }
            }
        )
    }

    else {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            },
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
            actions = {
                IconButton(onClick = {
                    // show the drop down menu
                    dropDownMenuExpanded = true
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More Vert",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                // drop down menu
                DropdownMenu(
                    expanded = dropDownMenuExpanded,
                    onDismissRequest = {
                        dropDownMenuExpanded = false
                    },
                    // play around with these values
                    // to position the menu properly
                    offset = DpOffset(x = 10.dp, y = (-60).dp)
                ) {
                    // this is a column scope
                    // items are added vertically

                    DropdownMenuItem(modifier = Modifier.padding(end = 30.dp), onClick = {
                        // 빈 액티비티로 이동
//                        openTutorialActivity(context)
                        //
                    }) {
                        Text(
                            text = "빈 액티비티",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                        )
                    }

                    DropdownMenuItem(modifier = Modifier.padding(end = 30.dp), onClick = {
                        openSettingIntent(context)
                    }) {
                        Text(
                            text = "설정",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                        )
                    }

                }
            }
        )
    }
}