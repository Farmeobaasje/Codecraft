package nl.codecraft.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import nl.codecraft.model.Repo
import nl.codecraft.ui.home.HomeScreen
import nl.codecraft.ui.navigation.Screen
import nl.codecraft.ui.repo_detail.RepoDetailScreen
import nl.codecraft.ui.repo_list.RepoListScreen
import nl.codecraft.ui.search.SearchScreen
import nl.codecraft.ui.settings.SettingsScreen
import timber.log.Timber

@Composable
fun MainScreen() {
    Timber.d("MainScreen composable called")
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    when (index) {
                        0 -> navController.navigate(Screen.Home.route) { 
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                        1 -> navController.navigate(Screen.Search.route) { 
                            popUpTo(Screen.Search.route) { inclusive = true }
                        }
                        2 -> navController.navigate(Screen.Settings.route) { 
                            popUpTo(Screen.Settings.route) { inclusive = true }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            onItemSelected = { selectedItem = it }
        )
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        BottomNavigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemSelected: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToRepoDetail = { repo ->
                    navController.navigate(Screen.RepoDetail.createRoute(repo.id))
                },
                onNavigateToSearch = {
                    onItemSelected(1)
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToSettings = {
                    onItemSelected(2)
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateToRepoList = { username ->
                    navController.navigate(Screen.RepoList.createRoute(username))
                },
                onNavigateToSettings = {
                    onItemSelected(2)
                    navController.navigate(Screen.Settings.route)
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
                languageFilter = null,
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
    }
}

private data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector
)

private val BottomNavigationItems = listOf(
    BottomNavigationItem("Home", Icons.Default.Home),
    BottomNavigationItem("Search", Icons.Default.Search),
    BottomNavigationItem("Settings", Icons.Default.Settings)
)
