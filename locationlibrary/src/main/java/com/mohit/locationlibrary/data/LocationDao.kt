package com.mohit.locationlibrary.data

import androidx.room.Dao
import androidx.room.Insert
import com.mohit.locationlibrary.model.LocationEntity

@Dao
interface LocationDao {
    @Insert
    fun insertLocation(location: LocationEntity)
}