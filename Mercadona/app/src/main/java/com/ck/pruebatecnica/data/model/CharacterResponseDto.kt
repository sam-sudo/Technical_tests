package com.ck.pruebatecnica.data.model

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)