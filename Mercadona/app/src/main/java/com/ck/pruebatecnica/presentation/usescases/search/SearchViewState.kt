package com.ck.pruebatecnica.presentation.usescases.search

import com.ck.pruebatecnica.domain.model.Character

data class SearchViewState (
    val isLoading: Boolean = false,
    val lasPageRequested: Int = 1,
    val characterList: List<Character> = emptyList(),
    val textToSearch: String = ""
)