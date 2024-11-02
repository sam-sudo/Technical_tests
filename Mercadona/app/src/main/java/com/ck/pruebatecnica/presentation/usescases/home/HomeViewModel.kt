package com.ck.pruebatecnica.presentation.usescases.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import com.ck.pruebatecnica.domain.repository.local.LocalCharacterRepository
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val localCharacterRepository: LocalCharacterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    init {
        refreshDatas()
    }

    fun refreshDatas() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            // Carga tus datos aquí
            loadCharacters()
            loadCharactersFromDB()
            withContext(Dispatchers.Main) {
                // Actualiza el estado en el hilo principal
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    // Cargar personajes desde la API
    suspend fun loadCharacters() {
        Log.e("CharacterViewModel", "loadCharacters ")

        try {
            _state.update {
                it.copy(isLoading = true)
            }

            val characters = characterRepository.updateCharacterFromApiToDb(1)

            // Puedes actualizar el estado aquí si es necesario
            Log.e("CharacterViewModel", "getAllCharacters -> $characters")
        } catch (e: Exception) {
            Log.e("CharacterViewModel", "Error al cargar personajes", e)
        }
    }

    // Cargar personajes desde la base de datos
    suspend fun loadCharactersFromDB() {
        Log.e("CharacterViewModel", "loadCharactersFromDB ")

        try {
            val characters = localCharacterRepository.getAllCharactersWithEpisodes()
            _state.update {
                it.copy(characterList = characters)
            }

            loadRandomCharacters(10) // Asumiendo que esta es otra función suspend
            loadTrendingCharacter()
            Log.e("CharacterViewModel", "getAllCharacters -> $characters")
        } catch (e: Exception) {
            Log.e("CharacterViewModel", "Error al cargar personajes", e)
        }
    }

    suspend fun loadRandomCharacters(maxNum: Int) {
        val randomList = ArrayList<CharacterWithEpisodes>()

        try {
            withTimeout(3000) { // Establece un límite de tiempo de 10 segundos
                while (randomList.size < maxNum) {
                    val randomCharacter = _state.value.characterList.random()
                    println("Iteración $randomCharacter")

                    if (!randomList.contains(randomCharacter)) {
                        randomList.add(randomCharacter) // Añádelo si no está en la lista
                    }
                    delay(100)
                }
            }
        } catch (e: TimeoutCancellationException) {
            // Aquí puedes manejar lo que sucede si se agota el tiempo
            println("Tiempo agotado. Se han seleccionado ${randomList.size} personajes.")
        }

        // Actualiza el estado con la lista, independientemente de si se completó o se agotó el tiempo
        _state.update {
            it.copy(
                characterRandomList = randomList
            )
        }
    }

    fun loadTrendingCharacter(){
        Log.e("CharacterViewModel", "character trending -> ${_state.value.characterList}")
        val sortedCharacters = _state.value.characterList
            .filter { character ->
                if (character.episodes == null){
                     false
                }else{
                    character.episodes.size > 5
                }
            }
            .sortedByDescending { character -> character.episodes?.size }
        _state.update {
            it.copy(
                characterTrendíngList = sortedCharacters
            )
        }
    }
}

