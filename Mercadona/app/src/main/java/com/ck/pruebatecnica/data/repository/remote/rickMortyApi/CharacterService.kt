package com.ck.pruebatecnica.data.repository.remote.rickMortyApi

import com.ck.pruebatecnica.data.model.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterService {

    @GET("api/character")
    suspend fun getCharacterByPage(@Query("page") page: Int): CharacterResponseDto

    @GET
    suspend fun getNextCharacters(@Url nextUrlPage: String): CharacterResponseDto
}