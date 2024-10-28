package com.ck.pruebatecnica.data.repository.remote.rickMortyApi

import androidx.room.Transaction
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.data.mapper.toEntity
import com.ck.pruebatecnica.data.model.CharacterDto
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteCharacterDatasource @Inject constructor(
    private val rickMortyService: CharacterService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao,
    private val origenDao: OriginDao
) : CharacterRepository {

    override suspend fun getCharacterByPage(page: Int): List<Character> {
        val charactersFromApi = rickMortyService.getCharacterByPage(page)
        withContext(Dispatchers.IO) {
            charactersFromApi.results.forEach { character ->
                insertCharacterWithRelations(character)
            }
        }
        return charactersFromApi.results.map { it.toDomain() }
    }

    override suspend fun updateCharacterFromApiToDb(page: Int): Boolean {
        val charactersFromApi = rickMortyService.getCharacterByPage(page)
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
        return rickMortyService.getNextCharacters(url).results.map { it.toDomain() }
    }

    //If an insert fails here, the rest will be undone.
    @Transaction
    private fun insertCharacterWithRelations(characterDto: CharacterDto) {
        characterDao.insert(characterDto.toEntity())
        origenDao.insertOrigin(characterDto.origin.toEntity(characterDto.id))
        locationDao.insertLocation(characterDto.location.toEntity(characterDto.id))
    }
}

