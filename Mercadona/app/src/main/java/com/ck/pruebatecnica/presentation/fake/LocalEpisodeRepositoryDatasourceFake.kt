package com.ck.pruebatecnica.presentation.fake

import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.repository.local.LocalCharacterRepositoryDatasource
import com.ck.pruebatecnica.data.repository.local.LocalEpisodeRepositoryDatasource

fun LocalEpisodeRepositoryDatasourceFake(): LocalEpisodeRepositoryDatasource {
    return localEpisodeRepositoryDatasource
}

// Instancia el repositorio y el ViewModel con los mocks
val localEpisodeRepositoryDatasource = LocalEpisodeRepositoryDatasource(
    characterDao = mockCharacterDao,
    locationDao = mockLocationDao,
    originDao = mockOriginDao,
    episodeDao = mockEpisodeDao
)