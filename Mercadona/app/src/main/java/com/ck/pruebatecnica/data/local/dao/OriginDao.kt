package com.ck.pruebatecnica.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ck.pruebatecnica.data.local.entities.OriginEntity

@Dao
interface OriginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrigin(origin: OriginEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrigins(origins: List<OriginEntity>): List<Long>

    @Update
    fun updateOrigin(origin: OriginEntity)

    @Delete
    fun deleteOrigin(origin: OriginEntity)

    @Query("SELECT * FROM origins WHERE originID = :id")
    fun getOriginById(id: Long): OriginEntity?

    @Query("SELECT * FROM origins")
    fun getAllOrigins(): List<OriginEntity>
}