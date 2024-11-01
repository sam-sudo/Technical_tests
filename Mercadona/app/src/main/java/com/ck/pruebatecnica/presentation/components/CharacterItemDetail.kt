package com.ck.pruebatecnica.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ck.pruebatecnica.R
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.repository.local.LocalCharacterRepositoryDatasource
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.local.LocalCharacterRepository
import com.ck.pruebatecnica.presentation.components.characterItem.CharacterItemViewModel
import com.ck.pruebatecnica.presentation.fake.LocalCharacterRepositoryDatasourceFake

@Composable
fun CharacterDetail(
    characterId: Long,
    characterItemViewModel: CharacterItemViewModel
) {
    val character: Character? by remember { mutableStateOf(characterItemViewModel.getCharacterById(characterId)) }

    LaunchedEffect(characterId) {
        Log.w("TAG", "CharacterDetail render")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (character != null) {
            Box {
                // Imagen del personaje
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character?.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Character image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                    ,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_error)
                )

                // Degradado que se mezcla con el fondo
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 500f,
                                endY = 1000f // Este valor puede ajustarse según la altura de la imagen
                            )
                        )
                )
            }

            // Contenido de información del personaje
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -80.dp), // Desplaza hacia arriba para que se mezcle con el degradado
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = character?.name ?: "Unknown",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // Asegúrate de que el texto sea legible sobre el degradado
                )

                Spacer(Modifier.height(0.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.background)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    character?.let { char ->
                        // Verifica si la propiedad no está vacía antes de mostrar el Card
                        if (!char.location?.name.isNullOrEmpty()) {
                            InfoLabelCard("Location", char.location?.name.toString(), "Location")
                        }
                        if (!char.status.isNullOrEmpty()) {
                            InfoLabelCard("Status", char.status, "Status")
                        }
                        if (!char.species.isNullOrEmpty()) {
                            InfoLabelCard("Species", char.species, "Species")
                        }
                        if (!char.type.isNullOrEmpty()) {
                            InfoLabelCard("Type", char.type, "Type")
                        }
                        if (!char.gender.isNullOrEmpty()) {
                            InfoLabelCard("Gender", char.gender, "Gender")
                        }
                        if (char.origin?.name?.isNotEmpty() == true) {
                            InfoLabelCard("Origin", char.origin.name, "Origin")
                        }
                    }
                }
                Text(
                    text = "Episodes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                if (character?.episodes != null) {
                    LazyRow {
                        items(character!!.episodes) { episode ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(200.dp)
                                    .width(80.dp),
                                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    Text(
                                        text = "${episode ?: "Unknown"}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun characterItemDetail() {
    val localCharacterRepositoryDatasourceFake = LocalCharacterRepositoryDatasourceFake()
    val localCharacterRepository = LocalCharacterRepository(localCharacterRepositoryDatasourceFake)
    val characterItemViewModel = CharacterItemViewModel(localCharacterRepository)

    // Llama a CharacterDetail en la preview con datos de ejemplo
    CharacterDetail(characterId = 2L, characterItemViewModel = characterItemViewModel)
}


