package com.ck.pruebatecnica.domain.repository.remote

import com.ck.pruebatecnica.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacterByPage(page: Int): List<Character>
    suspend fun updateCharacterFromApiToDb(page: Int): Boolean
    suspend fun getNextCharacters(url: String): List<Character>
}