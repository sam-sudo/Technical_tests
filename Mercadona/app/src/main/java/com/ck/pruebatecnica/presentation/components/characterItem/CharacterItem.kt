package com.ck.pruebatecnica.presentation.components.characterItem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ck.pruebatecnica.R
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import com.ck.pruebatecnica.presentation.usescases.home.HomeViewState

@Composable
fun CharacterItem(
    characterWithEpisodes: CharacterWithEpisodes,
    navController: NavController,
) {
    val character = characterWithEpisodes.character
    val episodes = characterWithEpisodes.episodes
    key(character.id) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image) // URL de la imagen
                        .apply {
                            crossfade(true)
                            allowHardware(false)
                            scale(Scale.FILL)
                            bitmapConfig(Bitmap.Config.RGB_565) // Reduce la calidad para optimizar rendimiento
                            size(128) // Tamaño de la imagen
                            error(R.drawable.image_error) // Imagen de error si falla la carga
                        }
                        .build(),
                    contentDescription = character.name,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop
                )*/
                    // En el Composable de la lista donde muestras los personajes
                    AsyncImage(
                        model = character.image,
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = character.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${episodes.size}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                IconButton(
                    onClick = {
                        Log.w("CharacterItem","character selected $character")
                        navController.navigate("characterDetail/${character.id}")
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.pencil),
                        contentDescription = "edit",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
}