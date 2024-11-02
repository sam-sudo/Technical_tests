package com.ck.pruebatecnica.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ck.pruebatecnica.data.local.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharacterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>): List<Long>

    @Update
    fun update(character: CharacterEntity): Int

    @Delete
    fun delete(character: CharacterEntity): Int

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE characterId = :id")
    fun getCharacterById(id: Long): CharacterEntity?

    @Query("DELETE FROM characters")
    fun deleteAllCharacters(): Int
}

