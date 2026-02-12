package com.eduardodenis.loteria.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eduardodenis.loteria.compose.detail.BetListDetailScreen
import com.eduardodenis.loteria.compose.home.HomeScreen
import com.eduardodenis.loteria.compose.lotofacil.LotofacilScreen
import com.eduardodenis.loteria.compose.megasena.MegaScreen
import com.eduardodenis.loteria.compose.quina.QuinaScreen

@Composable
fun LoteriaApp() {
    val navController = rememberNavController()
    LoteriaAppNavHost(navController)
}

enum class AppRouter(val route: String) {
    HOME("home"),
    MEGA_SENA("megasena"),
    QUINA("quina"),
    LOTOFACIL("lotofacil"),
    BET_LIST_DETAIL("betlistDetail")
}

@Composable
fun LoteriaAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRouter.HOME.route
    ) {
        composable(AppRouter.HOME.route) {
            HomeScreen { item ->
                val router = when (item.id) {
                    1 -> AppRouter.MEGA_SENA
                    2 -> AppRouter.QUINA
                    3 -> AppRouter.LOTOFACIL
                    else -> AppRouter.HOME
                }
                navController.navigate(router.route)
            }
        }
        composable(AppRouter.MEGA_SENA.route) {
            MegaScreen(
                onBackCLick = {
                    navController.navigateUp()
                },
                onMenuClick = {
                    navController.navigate(AppRouter.BET_LIST_DETAIL.route + "/$it")
                })
        }

        composable(AppRouter.QUINA.route) {
            QuinaScreen(
                onBackCLick = {
                    navController.navigateUp()
                },
                onMenuClick = {
                    navController.navigate(AppRouter.BET_LIST_DETAIL.route + "/$it")
                })
        }
        composable(AppRouter.LOTOFACIL.route) {
            LotofacilScreen(
                onBackCLick = {
                    navController.navigateUp()
                },
                onMenuClick = {
                    navController.navigate(AppRouter.BET_LIST_DETAIL.route + "/$it")
                })
        }

        composable(
            route = AppRouter.BET_LIST_DETAIL.route + "/{type}", arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                }
            )) {
            BetListDetailScreen()

        }
    }
}
