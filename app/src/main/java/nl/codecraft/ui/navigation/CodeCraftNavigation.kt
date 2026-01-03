package nl.codecraft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.codecraft.ui.repo_detail.RepoDetailScreen
import nl.codecraft.ui.repo_list.RepoListScreen
import nl.codecraft.ui.search.SearchScreen
import nl.codecraft.ui.settings.SettingsScreen
import nl.codecraft.ui.trending.TrendingScreen

@Composable
fun CodeCraftNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateToRepoList = { username ->
                    navController.navigate(Screen.RepoList.createRoute(username))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToTrending = {
                    navController.navigate(Screen.Trending.route)
                }
            )
        }
        composable(
            route = Screen.RepoList.route,
            arguments = Screen.RepoList.arguments
        ) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username") ?: ""
            RepoListScreen(
                username = username,
                onNavigateToRepoDetail = { repo ->
                    navController.navigate(Screen.RepoDetail.createRoute(repo.id))
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = Screen.RepoDetail.route,
            arguments = Screen.RepoDetail.arguments
        ) { navBackStackEntry ->
            val repoId = navBackStackEntry.arguments?.getLong("repoId") ?: 0L
            RepoDetailScreen(
                repoId = repoId,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = Screen.Trending.route) {
            TrendingScreen(
                onNavigateToRepoDetail = { repo ->
                    navController.navigate(Screen.RepoDetail.createRoute(repo.id))
                }
            )
        }
    }
}

sealed class Screen(
    val route: String
) {
    object Search : Screen("search")

    object RepoList : Screen("repo_list/{username}") {
        fun createRoute(username: String) = "repo_list/$username"
        val arguments = listOf(
            navArgument("username") {
                type = NavType.StringType
            }
        )
    }

    object RepoDetail : Screen("repo_detail/{repoId}") {
        fun createRoute(repoId: Long) = "repo_detail/$repoId"
        val arguments = listOf(
            navArgument("repoId") {
                type = NavType.LongType
            }
        )
    }

    object Settings : Screen("settings")

    object Trending : Screen("trending")
}
