package com.mohit.locationlibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "accuracy") val accuracy: Float?,
    @ColumnInfo(name = "timestampMillis") val timestampMillis: Long?,
    @ColumnInfo(name = "provider") val provider: String?,
)