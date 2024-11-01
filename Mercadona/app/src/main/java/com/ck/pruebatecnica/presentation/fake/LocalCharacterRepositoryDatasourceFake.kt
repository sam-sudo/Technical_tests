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

fun LocalCharacterRepositoryDatasourceFake(): LocalCharacterRepositoryDatasource {
    return localCharacterRepositoryDatasource
}
// Mocks de DAOs
val mockCharacterDao = object : CharacterDao {
    override fun insert(character: CharacterEntity): Long {
        TODO("Not yet implemented")
    }

    override fun insertAll(characters: List<CharacterEntity>): List<Long> {
        TODO("Not yet implemented")
    }

    override fun update(character: CharacterEntity): Int {
        TODO("Not yet implemented")
    }

    override fun delete(character: CharacterEntity): Int {
        TODO("Not yet implemented")
    }

    override fun getAllCharacters(): List<CharacterEntity> {
        TODO("Not yet implemented")
    }

    override fun getCharacterById(characterId: Long): CharacterEntity {
        return CharacterEntity(
            characterId = characterId,
            name = "Mock Character",
            status = "Alive",
            species = "Human",
            type = "Cyborg",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf("Episode 1", "Episode 2", "Episode 3")
        )
    }

    override fun deleteAllCharacters(): Int {
        TODO("Not yet implemented")
    }
    // Implementa otros métodos necesarios con valores de ejemplo
}

val mockLocationDao = object : LocationDao {
    // Implementa métodos con valores de ejemplo si es necesario
    override fun insertLocation(location: LocationEntity): Long {
        TODO("Not yet implemented")
    }

    override fun insertAllLocations(locations: List<LocationEntity>): List<Long> {
        TODO("Not yet implemented")
    }

    override fun updateLocation(location: LocationEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteLocation(location: LocationEntity) {
        TODO("Not yet implemented")
    }

    override fun getLocationByCharacterId(id: Long): LocationEntity? {
        return null
    }

    override fun getAllLocations(): List<LocationEntity> {
        TODO("Not yet implemented")
    }
}

val mockOriginDao = object : OriginDao {
    // Implementa métodos con valores de ejemplo si es necesario
    override fun insertOrigin(origin: OriginEntity): Long {
        TODO("Not yet implemented")
    }

    override fun insertAllOrigins(origins: List<OriginEntity>): List<Long> {
        TODO("Not yet implemented")
    }

    override fun updateOrigin(origin: OriginEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteOrigin(origin: OriginEntity) {
        TODO("Not yet implemented")
    }

    override fun getOriginByCharacterId(id: Long): OriginEntity? {
        return null
    }

    override fun getAllOrigins(): List<OriginEntity> {
        TODO("Not yet implemented")
    }
}

val mockEpisodeDao = object : EpisodeDao {
    override fun insert(episode: EpisodeEntity): Long {
        TODO("Not yet implemented")
    }

    override fun update(episode: EpisodeEntity): Int {
        TODO("Not yet implemented")
    }

    override fun getAllEpisodes(): List<EpisodeEntity> {
        TODO("Not yet implemented")
    }

    override fun getEpisodeById(id: Long): EpisodeEntity? {
        TODO("Not yet implemented")
    }

}

// Instancia el repositorio y el ViewModel con los mocks
val localCharacterRepositoryDatasource = LocalCharacterRepositoryDatasource(
    characterDao = mockCharacterDao,
    locationDao = mockLocationDao,
    originDao = mockOriginDao,
    episodeDao = mockEpisodeDao
)