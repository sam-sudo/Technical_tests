package com.ck.pruebatecnica.presentation.usescases.home

import com.ck.pruebatecnica.domain.model.Character

data class HomeViewState (
    val isLoading: Boolean = false,
    val lasPageRequested: Int = 1,
    val characterList: List<Character> = emptyList(),
    val characterRandomList: List<Character> = emptyList(),
    val characterTrendíngList: List<Character> = emptyList(),
    val textToSearch: String = ""
    )