package com.example.thronesapi.views


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thronesapi.viewmodels.CharactersViewModel



@Composable
fun MainScreen(
    charactersViewModel: CharactersViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characters_list") {
           composable("characters_list") {
                CharactersScreen(navController = navController, charactersViewModel = charactersViewModel)
            }
           composable("characters_detail") {
                CharacterDetailScreen(navController = navController, charactersViewModel = charactersViewModel)
            }
        }
    }


