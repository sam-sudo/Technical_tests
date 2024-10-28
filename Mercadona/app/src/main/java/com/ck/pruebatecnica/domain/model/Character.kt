package com.ck.pruebatecnica.domain.model

data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin? = Origin(),
    val location: Location? = Location(),
    val image: String,
    val episodes: List<String>
)

data class Location(
    val name: String? = "",
    val url: String? = ""
)

data class Origin(
    val name: String? = "",
    val url: String? = ""
)