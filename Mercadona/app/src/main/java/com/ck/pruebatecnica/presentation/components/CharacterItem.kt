package com.ck.pruebatecnica.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.ck.pruebatecnica.R
import com.ck.pruebatecnica.domain.model.Character

@Composable
fun CharacterItem(character: Character) {
    key(character.id) {
        val painter =
            rememberImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = character.image)
                    .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        allowHardware(false)
                        scale(Scale.FILL)
                        bitmapConfig(Bitmap.Config.RGB_565) // Reduce la calidad para optimizar rendimiento
                        size(128)
                        error(
                            R.drawable.image_error,
                        )
                    }).build()
            )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                )
                /*AsyncImage(
                    model = character.image, // URL de la imagen
                    contentDescription = character.name,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.image_error), // Imagen de error si falla la carga
                    placeholder = painterResource(id = R.drawable.image_error) // Imagen de carga
                )*/
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
                Icon(
                    painterResource(id = R.drawable.pencil), contentDescription = "Menu", modifier = Modifier
                    .size(20.dp))
            }
        }
    }

}