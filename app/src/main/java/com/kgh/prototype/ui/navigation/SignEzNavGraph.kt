package com.kgh.prototype.ui.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kgh.prototype.ui.home.HomeDestination
import com.kgh.prototype.ui.home.HomeScreen

@Composable
fun HexaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    activity: Activity,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navController = navController
            )
        }

//        composable(route = CabinetListScreenDestination.route+"/{mode}") {
//            backStackEntry ->
//            backStackEntry.arguments?.getString("mode")?.let {
//                CabinetInformationScreen(
//                    modifier = Modifier,
//                    navController = navController,
//                    signageViewModel =  viewModel3,
//                    detailViewModel = viewModel6,
//                    mode=it,
//                    onNavigateUp = { navController.navigateUp() },
//                )
//            }
//
//        }
//
//        composable(route = ErrorImageDestination.route+"/{x}/{y}/{resultId}") {
//            backStackEntry ->
//            backStackEntry.arguments?.let {
//                ErrorImageView(
//                    modifier = Modifier,
//                    onNavigateUp = { navController.navigateUp() },
//                    viewModel = viewModel5,
//                    x = it.getString("x")!!.toInt(),
//                    y = it.getString("y")!!.toInt(),
//                    resultId = it.getString("resultId")!!.toLong()
//                )
//            }
//        }
    }
}