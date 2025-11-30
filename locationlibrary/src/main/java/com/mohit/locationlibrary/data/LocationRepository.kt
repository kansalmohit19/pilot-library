package com.mohit.locationlibrary.data

import android.location.Location
import com.mohit.locationlibrary.model.LocationEntity
import com.mohit.locationlibrary.model.toEntity
import com.mohit.locationlibrary.tracker.LocationTracker
import com.mohit.locationlibrary.utils.LocationValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val tracker: LocationTracker, private val dao: LocationDao
) {

    private var lastLocation: Location? = null

    suspend fun getCurrentLocation(): Location? {
        return tracker.getLocation()
    }

    suspend fun onNewLocation(location: Location) {
        println("New Location latitude: ${location.latitude}, longitude: ${location.longitude}")
        if (LocationValidator.isValidLocation(location, lastLocation)) {
            println("Valid Location with latitude: ${location.latitude}, longitude: ${location.longitude}")
            saveLocation(location.toEntity())
        }
    }

    private fun saveLocation(entity: LocationEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertLocation(entity)
        }
    }

    //fun getAllLocations() = dao.getAll()
}