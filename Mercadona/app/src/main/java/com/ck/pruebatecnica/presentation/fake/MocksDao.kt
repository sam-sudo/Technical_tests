package com.ck.pruebatecnica.presentation.fake

import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.dao.crossRef.CharacterEpisodeDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.local.entities.crossRef.CharacterEpisodeCrossRef
import com.ck.pruebatecnica.data.local.entities.entityComplete.CharacterWithEpisodesEntity
import com.ck.pruebatecnica.data.local.entities.entityComplete.EpisodeWithCharactersEntity

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

    override fun getEpisodesByCharacterId(id: Long): List<EpisodeEntity>? {
        TODO("Not yet implemented")
    }

    override fun getEpisodeWithCharacters(episodeId: Int): EpisodeWithCharactersEntity? {
        TODO("Not yet implemented")
    }

    override fun getEpisodeById(id: Long): EpisodeEntity? {
        TODO("Not yet implemented")
    }

}

val mockCharacterWithEpisodeDao = object : CharacterEpisodeDao{
    override fun insertAllCrossRefs(crossRefs: List<CharacterEpisodeCrossRef>) {
        TODO("Not yet implemented")
    }

    override fun getCharactersWithEpisodes(): List<CharacterWithEpisodesEntity> {
        TODO("Not yet implemented")
    }

    override fun getCharacterWithEpisodes(characterId: Long): CharacterWithEpisodesEntity? {
        TODO("Not yet implemented")
    }

}
