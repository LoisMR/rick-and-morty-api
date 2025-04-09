package org.mathieu.cleanrmapi.domain.location

import org.mathieu.cleanrmapi.domain.location.models.Location

interface LocationRepository {
    suspend fun getLocationById(id: Int) : Location
}