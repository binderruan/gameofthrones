package com.example.thronesapi.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thronesapi.R
import com.example.thronesapi.viewmodels.CharactersViewModel

@Composable
fun CharacterDetailScreen(
    navController: NavController,
    charactersViewModel: CharactersViewModel
) {
    val uiState by charactersViewModel.charactersDetailScreenUiState.collectAsState()

    BackHandler {
        charactersViewModel.onBackPressed(navController)
    }

    CharacterDetail(
        imageUrl = uiState.imagemCharacter,
        nome = uiState.nomeCharacter,
        titulo = uiState.tituloCharacter,
        familia = uiState.familiaCharacter,
    )
}

@Composable
fun CharacterDetail(
    imageUrl: String,
    nome: String,
    titulo: String,
    familia: String,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "INFORMAÇÕES DO PERSONAGEM", textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 25.dp)
                .width(250.dp),
            fontSize = 18.sp
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.got),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
        )
        Text(
            text = "Nome: $nome",
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .padding(start = 55.dp)
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Título: $titulo",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .padding(start = 55.dp)
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Família: $familia",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .padding(start = 55.dp)
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
