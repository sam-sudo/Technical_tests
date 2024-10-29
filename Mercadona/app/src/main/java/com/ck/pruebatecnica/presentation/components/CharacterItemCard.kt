package com.ck.pruebatecnica.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ck.pruebatecnica.R

@Composable
fun CharacterItemCard(img: String, text: String) {
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
            error = painterResource(id = R.drawable.image_error), // Imagen de error si falla la carga
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