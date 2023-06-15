package com.example.thronesapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.thronesapi.models.Character
import com.example.thronesapi.network.Thronesapi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CharactersViewModel : ViewModel() {

    private var _MainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )

    private var _charactersDetailScreenUiState: MutableStateFlow<CharacterDetailScreenUiState> =
        MutableStateFlow(
            CharacterDetailScreenUiState()
        )
    val charactersDetailScreenUiState: StateFlow<CharacterDetailScreenUiState> =
        _charactersDetailScreenUiState.asStateFlow()

    private var _uiState: MutableStateFlow<CharactersUiState> =
        MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                _uiState.value = CharactersUiState.Success(
                    Thronesapi.retrofitService.getCharacter()
                )
            } catch (e: IOException) {
                _uiState.value = CharactersUiState.Error
            } catch (e: HttpException) {
                _uiState.value = CharactersUiState.Error
            }
        }
    }

    var detailCharacter: Boolean = false

    fun characterDetail(character: Character, navController: NavController) {
        detailCharacter = true
        _charactersDetailScreenUiState.update { currentState ->
            currentState.copy(
                nomeCharacter = character.name,
                tituloCharacter = character.title,
                familiaCharacter = character.family,
                imagemCharacter = character.imageUrl
            )
        }
        navigate(navController)
    }

    private fun navigate(navController: NavController) {
        if (_MainScreenUiState.value.screenName == "ListaPersonagens") {
            if (detailCharacter) {
                _MainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Character Detail",
                    )
                }
            } else {
                _MainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "ListaPersonagens",
                    )
                }

                _charactersDetailScreenUiState.update {
                    CharacterDetailScreenUiState()
                }
            }
            navController.navigate("characters_detail")
        } else {
            _MainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "ListaPersonagens",
                )
            }

            _charactersDetailScreenUiState.update {
                CharacterDetailScreenUiState()
            }

            detailCharacter = false
            navController.navigate("characters_list") {
                popUpTo("characters_list") {
                    inclusive = true
                }
            }
        }
    }

    fun onBackPressed(navController: NavController) {

        _MainScreenUiState.update { currentState ->
            currentState.copy(
                screenName = "ListaPersonagens",
            )
        }
        navController.popBackStack()
    }
}