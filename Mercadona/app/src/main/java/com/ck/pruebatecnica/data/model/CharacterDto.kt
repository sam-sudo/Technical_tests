package com.ck.pruebatecnica.data.model

data class CharacterDto(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDto,
    val location: LocationDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class OriginDto(
    val id: Long,
    val name: String,
    val url: String
)

data class LocationDto(
    val id: Long,
    val name: String,
    val url: String
)
