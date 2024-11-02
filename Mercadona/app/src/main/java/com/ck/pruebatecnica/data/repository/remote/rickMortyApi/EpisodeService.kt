package com.ck.pruebatecnica.data.repository.remote.rickMortyApi

import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.model.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface EpisodeService {

    @GET("api/episode/{ids}")
    suspend fun getMultipleEpisodesByIds(@Path("ids") ids: List<Int>): List<EpisodeEntity>
}