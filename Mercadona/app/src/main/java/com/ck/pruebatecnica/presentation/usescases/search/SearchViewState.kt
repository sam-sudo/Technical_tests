package com.ck.pruebatecnica.presentation.usescases.search

import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes

data class SearchViewState (
    val isLoading: Boolean = false,
    val lasPageRequested: Int = 1,
    val characterList: List<CharacterWithEpisodes> = emptyList(),
    val textToSearch: String = ""
)