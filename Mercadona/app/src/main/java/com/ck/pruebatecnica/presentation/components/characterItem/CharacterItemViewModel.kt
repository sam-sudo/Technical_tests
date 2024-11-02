package com.ck.pruebatecnica.presentation.components.characterItem

import androidx.lifecycle.ViewModel
import com.ck.pruebatecnica.data.local.entities.entityComplete.CharacterWithEpisodesEntity
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import com.ck.pruebatecnica.domain.repository.local.LocalCharacterRepository
import com.ck.pruebatecnica.domain.repository.local.LocalEpisodeRepository
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import com.ck.pruebatecnica.presentation.usescases.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CharacterItemViewModel @Inject constructor(
    private val localCharacterRepository: LocalCharacterRepository,
    private val localEpisodeRepository: LocalEpisodeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    fun getCharacterWithEpisodesById(id: Long): CharacterWithEpisodes? = runBlocking{
        localCharacterRepository.getCharacterWithEpisodesByID(id)
    }

    fun getEpisodeById(id: Long): Episode? = runBlocking{
        localEpisodeRepository.getEpisodeById(id)
    }
}