package com.ck.pruebatecnica.domain.repository.local

import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.data.repository.local.LocalEpisodeRepositoryDatasource
import javax.inject.Inject

class LocalEpisodeRepository  @Inject constructor(
    private val localEpisodeDatasource: LocalEpisodeRepositoryDatasource,
) :
    EpisodeRepository {
    override suspend fun getEpisodeById(id: Long): Episode? {
        return localEpisodeDatasource.getEpisodeById(id)
    }

    override suspend fun insertEpisode(episode: EpisodeEntity): Long {
        return localEpisodeDatasource.insertEpisode(episode)
    }


}