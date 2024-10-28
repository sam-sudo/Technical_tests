package com.ck.pruebatecnica.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ck.pruebatecnica.data.local.entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: LocationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLocations(locations: List<LocationEntity>): List<Long>

    @Update
    fun updateLocation(location: LocationEntity)

    @Delete
    fun deleteLocation(location: LocationEntity)

    @Query("SELECT * FROM locations WHERE locationId = :id")
    fun getLocationById(id: Long): LocationEntity?

    @Query("SELECT * FROM locations")
    fun getAllLocations(): List<LocationEntity>
}