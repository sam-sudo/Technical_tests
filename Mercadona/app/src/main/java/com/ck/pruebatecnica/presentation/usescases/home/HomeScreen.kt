package com.ck.pruebatecnica.presentation.usescases.home

import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ck.pruebatecnica.R
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.ui.theme.PruebaTecnicaTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    navController: NavController,
    homeState: StateFlow<HomeViewState>,
    viewModel: HomeViewModel,
) {
    val state by homeState.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre los elementos
    ) {
        // Sección para el botón de refresh
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    viewModel.refreshDatas()
                }) {
                    Text("Refresh")
                }
            }
        }

        // Sección para descubrir más personajes
        item {
            DiscoverMoreCharacter(state)
        }

        // Sección de personajes en tendencia
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Trending characters",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                // Lista de personajes
                LazyColumn(
                    state = listState,
                    modifier = Modifier.height(400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.characterTrendíngList, key = { character -> character.id }) { character ->
                        TrendingCharacterItem(character)
                    }
                }
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



@Composable
fun CharacterCard(img: String, text: String) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)

    ) {
        DarkImageWithText(img = img, text = text)
    }
}

@Composable
fun DarkImageWithText(img: String, text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        /*Image(
            painter = painterResource(id = imageResource),
            contentDescription = text,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )*/
        AsyncImage(
            model = img,
            contentDescription = text,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.image), // Imagen de error si falla la carga
            placeholder = painterResource(id = R.drawable.pencil) // Imagen de carga
        )

        // Capa oscura encima de la imagen
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Fondo oscuro con opacidad
        )

        // Texto encima
        Text(
            text = text,
            color = Color.White, // Color del texto
            modifier = Modifier
                .align(Alignment.Center) // Centrar el texto
                .padding(16.dp) // Espaciado
        )
    }
}


@Composable
fun DiscoverMoreCharacter(state: HomeViewState) {
    // Discover More Characters Section
    Text(
        text = "Discover more characters",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Añade espacio entre los ítems
    ) {
        items(state.characterRandomList) { character ->
            CharacterCard(character.image, character.name)
        }
    }
}


@Composable
fun TrendingCharacterItem(character: Character) {
    key(character.id) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = character.image, // URL de la imagen
                    contentDescription = character.name,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.image), // Imagen de error si falla la carga
                    placeholder = painterResource(id = R.drawable.image) // Imagen de carga
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${character.episodes.size}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            IconButton(
                onClick = { /* Handle action */ }) {
                Icon(painterResource(id = R.drawable.pencil), contentDescription = "Menu", modifier = Modifier
                    .size(20.dp))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PruebaTecnicaTheme {
        /*val navController = rememberNavController()
        val sampleCharacters = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "Scientist",
                gender = "Male",
                origin = Origin(name = "Earth", url = "https://example.com/origin/1"),
                location = Location(name = "Earth", url = "https://example.com/location/1"),
                image = "https://example.com/images/rick.png",
                episodes = listOf("S01E01", "S01E02")
            ),
            Character(
                id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                type = "Student",
                gender = "Male",
                origin = Origin(name = "Earth", url = "https://example.com/origin/2"),
                location = Location(name = "Earth", url = "https://example.com/location/2"),
                image = "https://example.com/images/morty.png",
                episodes = listOf("S01E01", "S01E03")
            ),
            Character(
                id = 3,
                name = "Summer Smith",
                status = "Alive",
                species = "Human",
                type = "Student",
                gender = "Female",
                origin = Origin(name = "Earth", url = "https://example.com/origin/3"),
                location = Location(name = "Earth", url = "https://example.com/location/3"),
                image = "https://example.com/images/summer.png",
                episodes = listOf("S01E02", "S01E04")
            ),
            Character(
                id = 4,
                name = "Beth Smith",
                status = "Alive",
                species = "Human",
                type = "Surgeon",
                gender = "Female",
                origin = Origin(name = "Earth", url = "https://example.com/origin/4"),
                location = Location(name = "Earth", url = "https://example.com/location/4"),
                image = "https://example.com/images/beth.png",
                episodes = listOf("S01E05", "S01E06")
            ),
            Character(
                id = 5,
                name = "Jerry Smith",
                status = "Alive",
                species = "Human",
                type = "Advertising Agent",
                gender = "Male",
                origin = Origin(name = "Earth", url = "https://example.com/origin/5"),
                location = Location(name = "Earth", url = "https://example.com/location/5"),
                image = "https://example.com/images/jerry.png",
                episodes = listOf("S01E07", "S01E08")
            ),
            Character(
                id = 6,
                name = "Birdperson",
                status = "Alive",
                species = "Bird-Person",
                type = "",
                gender = "Male",
                origin = Origin(name = "Bird World", url = "https://example.com/origin/6"),
                location = Location(name = "Bird World", url = "https://example.com/location/6"),
                image = "https://example.com/images/birdperson.png",
                episodes = listOf("S02E01", "S02E02")
            ),
            Character(
                id = 7,
                name = "Mr. Meeseeks",
                status = "Alive",
                species = "Meeseeks",
                type = "",
                gender = "Male",
                origin = Origin(name = "Unknown", url = "https://example.com/origin/7"),
                location = Location(name = "Unknown", url = "https://example.com/location/7"),
                image = "https://example.com/images/meeseeks.png",
                episodes = listOf("S01E09", "S01E10")
            ),
            Character(
                id = 8,
                name = "Evil Morty",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = Origin(name = "Unknown", url = "https://example.com/origin/8"),
                location = Location(name = "Unknown", url = "https://example.com/location/8"),
                image = "https://example.com/images/evil_morty.png",
                episodes = listOf("S03E01", "S03E02")
            ),
            Character(
                id = 9,
                name = "Jessica",
                status = "Alive",
                species = "Human",
                type = "Student",
                gender = "Female",
                origin = Origin(name = "Earth", url = "https://example.com/origin/9"),
                location = Location(name = "Earth", url = "https://example.com/location/9"),
                image = "https://example.com/images/jessica.png",
                episodes = listOf("S01E11", "S01E12")
            ),
            Character(
                id = 10,
                name = "Squanchy",
                status = "Alive",
                species = "Cat-Person",
                type = "",
                gender = "Male",
                origin = Origin(name = "Cat World", url = "https://example.com/origin/10"),
                location = Location(name = "Cat World", url = "https://example.com/location/10"),
                image = "https://example.com/images/squanchy.png",
                episodes = listOf("S02E03", "S02E04")
            )
        )

        val state: StateFlow<HomeViewState> = MutableStateFlow(
            HomeViewState(
                characterList = sampleCharacters
            )
        )

         val fastSearchByTagsViewModel: FastSearchByTagsViewModel by viewModels()
         val homeViewModel: HomeViewModel by viewModels()
        val characterRepository = CharacterRepository() // Asegúrate de que esto funcione correctamente
        val localCharacterRepository = LocalCharacterRepository() // Igualmente aquí
        val sampleViewModel = HomeViewModel(characterRepository, localCharacterRepository)

        // Llamar a HomeScreen con datos de prueba
        HomeScreen(navController = navController, homeState = state, viewModel = sampleViewModel)*/
    }
}