package com.ck.pruebatecnica.domain.repository.remote

import com.ck.pruebatecnica.data.repository.remote.rickMortyApi.RemoteCharacterDatasource
import com.ck.pruebatecnica.domain.model.Character
import javax.inject.Inject

class RemoteCharacterRepository  @Inject constructor(private val characterDatasource: RemoteCharacterDatasource) :
    CharacterRepository {
    override suspend fun getCharacterByPage(page: Int): List<Character> {
        return characterDatasource.getCharacterByPage(page)
    }

    override suspend fun updateCharacterFromApiToDb(page: Int): Boolean {
        return characterDatasource.updateCharacterFromApiToDb(page)
    }

    override suspend fun getNextCharacters(url: String): List<Character> {
        return characterDatasource.getNextCharacters(url)

    }
}