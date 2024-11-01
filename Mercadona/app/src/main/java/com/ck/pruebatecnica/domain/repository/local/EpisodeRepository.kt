package com.ck.pruebatecnica.domain.repository.local

import com.ck.pruebatecnica.data.model.Episode

interface EpisodeRepository {
    suspend fun getEpisodeById(id: Long): Episode?
}