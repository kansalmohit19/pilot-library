package com.mohit.locationlibrary.utils

import android.location.Location
import kotlin.math.abs

object LocationValidator {

    // tuning parameters
    private const val MIN_TIME_DIFF_SEC = 2.0           // seconds
    private const val MAX_ACCEPTABLE_ACCURACY_M = 50f   // meters
    private const val MAX_JUMP_DISTANCE_M = 500.0       // meters
    private const val MAX_SPEED_KMPH = 150.0            // km/h
    private const val MAX_BEARING_DIFF = 45.0          // degrees

    fun isValidLocation(newLocation: Location, lastLocation: Location?): Boolean {
        if (lastLocation == null) return true

        val timeDiffSec = (newLocation.time - lastLocation.time) / 1000.0
        if (timeDiffSec < MIN_TIME_DIFF_SEC) return false

        val distance = lastLocation.distanceTo(newLocation).toDouble()
        val speedMps = if (timeDiffSec > 0) distance / timeDiffSec else Double.MAX_VALUE
        val speedKmph = speedMps * 3.6

        if (newLocation.accuracy > MAX_ACCEPTABLE_ACCURACY_M) return false
        if (distance > MAX_JUMP_DISTANCE_M && timeDiffSec < 5) return false
        if (speedKmph > MAX_SPEED_KMPH) return false

        if (lastLocation.hasBearing() && newLocation.hasBearing()) {
            val bearingDiff = abs(newLocation.bearing - lastLocation.bearing)
            if (bearingDiff > MAX_BEARING_DIFF && speedKmph > 10) return false
        }

        return true
    }
}