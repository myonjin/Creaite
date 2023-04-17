package com.kgh.prototype.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kgh.prototype.HexaTopAppBar
import com.kgh.prototype.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = "Hexa"
}

@Composable
fun HomeScreen(
//    navigateToPicture: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    androidx.compose.material.Scaffold(
        topBar = {
            HexaTopAppBar(
                title = "Hexa",
                canNavigateBack = false
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                Text(text= "하단 바")
            }
        }
    ) { innerPadding -> // default Scaffold 내부 다른 구조와 겹치지 않는 적절한 값.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
            ) {

                Spacer(modifier = Modifier.padding(5.dp))

                Spacer(modifier = Modifier.padding(8.dp))
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(text= " 몸 통")
                }
            }
        }
    }
}