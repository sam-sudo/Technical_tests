package com.ck.pruebatecnica.data.repository.local

import android.util.Log
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.domain.repository.local.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalEpisodeRepositoryDatasource @Inject constructor(
    private val characterDao: CharacterDao,
    private val episodeDao: EpisodeDao,
    private val locationDao: LocationDao,
    private val originDao: OriginDao,
) :
    EpisodeRepository {

    override suspend fun getEpisodeById(id: Long): Episode? {
        return withContext(Dispatchers.IO) {
            val characterId = id
            Log.w("TAG","Episode Id $characterId")

            val episode = episodeDao.getEpisodeById(characterId)

            Log.w("TAG","episode $episode")

            episode?.toDomain()
        }
    }

    override suspend fun insertEpisode(episode: EpisodeEntity): Long {
        return episodeDao.insert(episode)
    }
}