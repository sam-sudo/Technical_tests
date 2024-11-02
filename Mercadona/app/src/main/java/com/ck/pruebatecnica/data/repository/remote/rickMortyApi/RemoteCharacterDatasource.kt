package com.ck.pruebatecnica.data.repository.remote.rickMortyApi

import android.util.Log
import androidx.room.Transaction
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.dao.crossRef.CharacterEpisodeDao
import com.ck.pruebatecnica.data.local.entities.crossRef.CharacterEpisodeCrossRef
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.data.mapper.toEntity
import com.ck.pruebatecnica.data.model.CharacterDto
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteCharacterDatasource @Inject constructor(
    private val characterService: CharacterService,
    private val episodeService: EpisodeService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao,
    private val origenDao: OriginDao,
    private val episodeDao: EpisodeDao,
    private val charaEpisodeDao: CharacterEpisodeDao
) : CharacterRepository {

    override suspend fun getCharacterByPage(page: Int): List<Character> {
        val charactersFromApi = characterService.getCharacterByPage(page)
        withContext(Dispatchers.IO) {
            charactersFromApi.results.forEach { character ->
                insertCharacterWithRelations(character)
            }
        }
        return charactersFromApi.results.map { it.toDomain() }
    }

    override suspend fun updateCharacterFromApiToDb(page: Int): Boolean {
        val charactersFromApi = characterService.getCharacterByPage(page)
        return withContext(Dispatchers.IO) {
            try {
                charactersFromApi.results.forEach { character ->
                    insertCharacterWithRelations(character)
                }
                return@withContext true
            }catch (e: Exception){
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    override suspend fun getNextCharacters(url: String): List<Character> {
        return characterService.getNextCharacters(url).results.map { it.toDomain() }
    }

    //If an insert fails here, the rest will be undone.
    @Transaction
    private suspend fun insertCharacterWithRelations(characterDto: CharacterDto) {
        val characterId = characterDao.insert(characterDto.toEntity())
        origenDao.insertOrigin(characterDto.origin.toEntity(characterDto.id))
        locationDao.insertLocation(characterDto.location.toEntity(characterDto.id))
        insertCharacterWithEpisodes(characterId,characterDto.episode)
    }

    private suspend fun insertCharacterWithEpisodes(characterId: Long, episodes: List<String>) {
        Log.w("remoteCharacterDatasource","character id $characterId")
        Log.w("remoteCharacterDatasource","episodes  $episodes")


        val ids = episodes.map { episode ->
            val id = episode.split("/").lastOrNull()?.toIntOrNull()
            id
        }
        Log.w("remoteCharacterDatasource","ids  $ids")

        if (!ids.isNullOrEmpty()){
            val episodesFromApi = episodeService.getMultipleEpisodesByIds(ids as List<Int>)
            Log.w("remoteCharacterDatasource","episodesFromApi  ${episodesFromApi}")

            // 2. Inserta todos los episodios y obtiene sus IDs
            val episodeIds = mutableListOf<Long>()
            for (episode in episodesFromApi) {
                // Inserta el episodio y agrega el ID a la lista
                val episodeId = episodeDao.insert(episode)
                episodeIds.add(episodeId)
            }

            // 3. Crear las relaciones en la tabla intermedia
            val crossRefs = episodeIds.map { episodeId ->
                CharacterEpisodeCrossRef(characterId = characterId, episodeId = episodeId)
            }

            // 4. Inserta las relaciones en la tabla intermedia
            charaEpisodeDao.insertAllCrossRefs(crossRefs)

        }else{
            Log.w("remoteCharacterDatasource","charactersFromApi  empty")
        }
    }

}

