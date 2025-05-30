package com.ck.pruebatecnica

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ck.pruebatecnica.presentation.usescases.fastSearchByTags.FastSearchByTagsScreen
import com.ck.pruebatecnica.presentation.usescases.fastSearchByTags.FastSearchByTagsViewModel
import com.ck.pruebatecnica.presentation.usescases.home.HomeScreen
import com.ck.pruebatecnica.presentation.usescases.home.HomeViewModel
import com.ck.pruebatecnica.presentation.usescases.search.SearchScreen
import com.ck.pruebatecnica.presentation.usescases.search.SearchViewModel
import com.ck.pruebatecnica.ui.theme.PruebaTecnicaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val fastSearchByTagsViewModel: FastSearchByTagsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaTecnicaTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(fastSearchByTagsViewModel,homeViewModel,searchViewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(
    fastSearchByTagsViewModel: FastSearchByTagsViewModel,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel
) {
    val navController = rememberNavController()
    val stateFastSearchByTags = fastSearchByTagsViewModel.state
    val stateHome = homeViewModel.state
    val stateSearch = searchViewModel.state
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Usa un Column para organizar el contenido
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "fastSearch", Modifier.weight(1f)) {
            composable("fastSearch") {
                // Usa un estado para determinar si la HomeScreen está lista
                FastSearchByTagsScreen(
                        navController,
                        stateFastSearchByTags,
                        fastSearchByTagsViewModel,
                        searchViewModel
                    )
            }
            navigation(startDestination = "home", route = "main") {
                composable("home") {
                    // Usa un estado para determinar si la HomeScreen está lista
                    HomeScreen(
                        navController = navController,
                        homeState = stateHome,
                        viewModel = homeViewModel
                    )
                }
                composable("search") {
                    SearchScreen(
                        navController,
                        stateSearch,
                        searchViewModel
                    )
                }
            }
        }

        // Muestra el BottomNavigation solo si no estás en la fastScreen
        if (currentRoute != "fastScreen") {
            BottomNavigationComponent(navController, currentRoute)
        }
    }
}


sealed class BottomNavigationScreens(val route: String, val label: String, val icon: Int) {
    object Home : BottomNavigationScreens("home", "Home", R.drawable.home)
    object Search : BottomNavigationScreens("search", "Search", R.drawable.search)
    companion object {
        val values = listOf(Home, Search) // Lista de pantallas
    }
}

@Composable
fun BottomNavigationComponent(navController: NavHostController,currentRoute:String?) {
    NavigationBar(
        modifier = Modifier
            .height(50.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {

        BottomNavigationScreens.values.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                    painterResource(id = screen.icon),
                        modifier = Modifier.height(20.dp),
                    contentDescription = screen.label
                ) },
                selected = currentRoute == screen.route,
                onClick = {
                    Log.w("mainActivity","navigate to ${screen.route}")
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { // Regresar al destino inicial si es necesario
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}
