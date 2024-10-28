package com.ck.pruebatecnica.presentation.usescases.fastSearchByTags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ck.pruebatecnica.presentation.usescases.search.SearchViewModel
import com.ck.pruebatecnica.ui.theme.PruebaTecnicaTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FastSearchByTagsScreen(
    navController: NavController,
    state: StateFlow<FastSearchByTagsViewState>,
    fastSearchByTagsViewModel: FastSearchByTagsViewModel,
    searchViewModel: SearchViewModel,
){

    val state by state.collectAsState()

    PruebaTecnicaTheme {
        Surface(
            modifier = Modifier.fillMaxSize().statusBarsPadding().systemBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                    ) {
                        Button(
                            modifier = Modifier
                                .width(100.dp)
                            ,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            onClick = {
                                navController.navigate("home") {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }) {
                            Text(
                                text = "Skip",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                        }
                    }

                    Spacer(Modifier.height(50.dp))

                    Text(
                        text = "Search for Characters:",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()

                    )

                    FlowRow(
                        maxItemsInEachRow = 3,
                        modifier = Modifier
                            .padding(0.dp)
                    ) {
                        state.categoryList.forEach { category ->
                            FlowRow {
                                Button(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .wrapContentSize(),
                                    onClick = {
                                        fastSearchByTagsViewModel.toggleCategorySelection(category)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = if (state.selectedCategories.contains(category))
                                            Color.White
                                        else
                                            Color.Black,
                                        containerColor = if (state.selectedCategories.contains(category))
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        else
                                            MaterialTheme.colorScheme.surface
                                    ),
                                    shape = if (state.selectedCategories.contains(category)) {
                                        Shapes().medium
                                    } else
                                        Shapes().medium
                                ) {
                                    Text(
                                        text = category,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                        }
                    }

                    Text(
                        text = "*Select one or more",
                        fontSize = 10.sp
                    )
                }

                Button(
                    onClick = {
                        searchViewModel.loadCharacterByFilter(state.selectedCategories)
                        navController.navigate("search") {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Explore")
                }
            }
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
     val fastSearchByTagsViewModel: FastSearchByTagsViewModel by viewModels()
    val navController = rememberNavController()
    val stateFastSearchByTags = fastSearchByTagsViewModel.state
    FastSearchByTagsScreen(navController,stateFastSearchByTags,fastSearchByTagsViewModel)
}*/
