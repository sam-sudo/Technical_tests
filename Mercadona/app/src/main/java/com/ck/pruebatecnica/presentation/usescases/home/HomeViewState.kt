package com.ck.pruebatecnica.presentation.usescases.home

import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes

data class HomeViewState (
    val isLoading: Boolean = false,
    val lasPageRequested: Int = 1,
    val characterList: List<CharacterWithEpisodes> = emptyList(),
    val characterRandomList: List<CharacterWithEpisodes> = emptyList(),
    val characterTrendíngList: List<CharacterWithEpisodes> = emptyList(),
    val textToSearch: String = ""
    )