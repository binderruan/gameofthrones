package com.example.thronesapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thronesapi.models.Character
import com.example.thronesapi.viewmodels.CharactersViewModel
import com.example.thronesapi.viewmodels.CharactersUiState
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter
import com.example.thronesapi.R

@Composable
fun CharactersScreen(
    navController: NavController,
    charactersViewModel: CharactersViewModel = viewModel()
) {
    val uiState by charactersViewModel.uiState.collectAsState()
    when (uiState) {
        is CharactersUiState.Loading -> LoadingScreen()
        is CharactersUiState.Success -> CharactersList(
            (uiState as CharactersUiState.Success).characters,
            onClickCharacter = { charactersViewModel.characterDetail(it, navController) })

        is CharactersUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = R.drawable.carregandoo).apply(block = {
                }).build()
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = R.drawable.erro).apply(block = {
                }).build()
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun CharactersList(
    characters: List<Character>,
    onClickCharacter: (Character) -> Unit,
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        columns = GridCells.Fixed(2)
    ) {
        items(characters) { character ->
            CharactersEntry(
                character = character,
                onClickCharacter = { onClickCharacter(character) }
            )
        }
    }
}

@Composable
fun CharactersEntry(
    character: Character,
    onClickCharacter: () -> Unit,
) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }
    Card(
        modifier = Modifier.padding(6.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                    .clickable { onClickCharacter() },
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box() {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.imageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.got),
                        contentDescription = character.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RectangleShape)
                            .onGloballyPositioned {
                                width.value = it.size.width / density
                                height.value = it.size.height / density
                            }
                    )
                    Box(
                        modifier = Modifier
                            .size(
                                width = width.value.dp,
                                height = height.value.dp
                            )
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color.Black),
                                    300F,
                                    600F
                                )
                            )
                    ) {

                    }
                    Text(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                        text = character.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}



