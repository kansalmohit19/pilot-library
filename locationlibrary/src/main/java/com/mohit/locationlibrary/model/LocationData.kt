package com.mohit.locationlibrary.model

import android.location.Location

fun Location.toEntity() = LocationEntity(
    latitude = this.latitude,
    longitude = this.longitude,
    accuracy = this.accuracy,
    timestampMillis = System.currentTimeMillis(),
    provider = this.provider
)
