package com.ck.pruebatecnica.presentation.components.characterItem

import androidx.lifecycle.ViewModel
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.local.LocalCharacterRepository
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import com.ck.pruebatecnica.presentation.usescases.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CharacterItemViewModel @Inject constructor(
    private val localCharacterRepository: LocalCharacterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    fun getCharacterById(id: Long): Character? = runBlocking{
        localCharacterRepository.getCharacterByID(id)
    }

    fun getEpisodeById(id: Long): Episode? = runBlocking{
        localCharacterRepository.getEpisodeById(id)
    }
}