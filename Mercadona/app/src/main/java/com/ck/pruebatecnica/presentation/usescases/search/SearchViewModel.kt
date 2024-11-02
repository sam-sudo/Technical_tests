package com.ck.pruebatecnica.presentation.usescases.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import com.ck.pruebatecnica.domain.repository.local.LocalCharacterRepository
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val localCharacterRepository: LocalCharacterRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SearchViewState())
    val state = _state.asStateFlow()

    var searchList = ArrayList<CharacterWithEpisodes>()

    init {
        loadCharactersFromDB()
    }

    fun loadCharactersFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("CharacterViewModel", "loadCharactersFromDB ")

            try {
                val characters = localCharacterRepository.getAllCharactersWithEpisodes()
                searchList = ArrayList<CharacterWithEpisodes>()
                searchList.addAll(characters)
                _state.update {
                    it.copy(characterList = characters, isLoading = false)
                }
                Log.e("CharacterViewModel", "getAllCharacters -> $characters")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error al cargar personajes", e)
            }
        }
    }

    fun loadCharacterByFilter(filterText:String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("CharacterViewModel", "loadCharacterByFilter ")

            try {
                val characterFiltered = searchList.filter {it.character.name.toLowerCase().contains(filterText.toLowerCase())}
                _state.update {
                    it.copy(characterList = characterFiltered, isLoading = false)
                }
                Log.e("CharacterViewModel", "characterFiltered -> $characterFiltered")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error al cargar personajes", e)
            }
        }
    }

    fun loadCharacterByFilter(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("CharacterViewModel", "loadCharacterByFilter list $list ")

            try {
                val characterFiltered = searchList.filter { it ->
                    // Filtrar por cualquier propiedad
                    val character = it.character
                    list.any { filterString ->
                                character.name.contains(filterString, ignoreCase = true) ||
                                character.status.contains(filterString, ignoreCase = true) ||
                                character.species.contains(filterString, ignoreCase = true) ||
                                character.type.contains(filterString, ignoreCase = true) ||
                                character.gender.contains(filterString, ignoreCase = true) ||
                                (character.origin?.name?.contains(filterString, ignoreCase = true) ?: false) ||
                                (character.location?.name?.contains(filterString, ignoreCase = true) ?: false)
                    }
                }

                _state.update {
                    it.copy(characterList = characterFiltered, isLoading = false)
                }
                Log.e("CharacterViewModel", "characterFiltered -> $characterFiltered")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error al cargar personajes", e)
            }
        }
    }


    fun updateTextSearch(text: String){
        _state.update {
            it.copy(
                textToSearch = text
            )
        }
        loadCharacterByFilter(text)
    }
}