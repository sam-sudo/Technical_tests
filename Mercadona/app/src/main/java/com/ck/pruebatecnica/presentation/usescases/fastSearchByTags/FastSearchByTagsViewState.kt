package com.ck.pruebatecnica.presentation.usescases.fastSearchByTags

data class FastSearchByTagsViewState (
    val isLoading: Boolean = false,
    val lasPageRequested: Int = 1,
    val categoryList: List<String> = listOf(
        "Humanoid", "Male", "Mortys", "Citadel of Ricks", "Ricks",
        "Alien", "Female", "Cromulon", "Alive", "Dead",
        "Parasite", "Earth (C-137)", "Mythological Creature"
    ),
    var selectedCategories: List<String> = emptyList()
)