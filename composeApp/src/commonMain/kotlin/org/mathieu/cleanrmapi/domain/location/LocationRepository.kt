package org.mathieu.cleanrmapi.domain.location

import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Defines the contract for accessing location-related data.
 *
 * This interface is implemented in the data layer and used in the domain/presentation
 * layers to retrieve location information in a platform-agnostic and testable way.
 */
interface LocationRepository {

    /**
     * Retrieves the details of a specific location by its ID.
     *
     * @param id The unique identifier of the location to be fetched.
     * @return The corresponding [Location] object.
     */
    suspend fun getLocationById(id: Int): Location
}