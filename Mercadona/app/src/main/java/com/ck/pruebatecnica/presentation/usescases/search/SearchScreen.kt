package com.ck.pruebatecnica.presentation.usescases.search

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ck.pruebatecnica.R
import com.ck.pruebatecnica.presentation.components.characterItem.CharacterItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    navController: NavController,
    searchState: StateFlow<SearchViewState>,
    viewModel: SearchViewModel,
) {
    val state by searchState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        // Contenido principal
        Column(modifier = Modifier.fillMaxSize()) {
            // Search Bar
            SearchBar(
                searchText = state.textToSearch,
                onSearchTextChange = { newText ->
                    viewModel.updateTextSearch(newText)
                }
            )

            // Lista de personajes
            LazyColumn(
                //state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.characterList, key = { character -> character.character.id }) { character ->
                    CharacterItem(character,navController)
                }
            }
        }

        // Indicador de carga que se superpone
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current // Obtener el FocusManager
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            // ... do anything you want here with `isKeyboardOpen`
            if (!isKeyboardOpen){
                focusManager.clearFocus()
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(60.dp),
            color = Color.White, // Set the background color to white
            shape = RoundedCornerShape(8.dp) // Round the corners
        ) {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                label = { Text("Search") },
                modifier = Modifier.weight(1f),
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.search),
                        modifier = Modifier.height(20.dp),
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent, // Sin indicador inferior
                    unfocusedIndicatorColor = Color.Transparent // Sin indicador inferior
                )
            )
        }

    }
}