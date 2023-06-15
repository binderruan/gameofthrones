package com.example.thronesapi.viewmodels

import com.example.thronesapi.models.Character

sealed interface CharactersUiState {

    object Loading : CharactersUiState

    data class Success(val characters: List<Character>) : CharactersUiState

    object Error : CharactersUiState
}