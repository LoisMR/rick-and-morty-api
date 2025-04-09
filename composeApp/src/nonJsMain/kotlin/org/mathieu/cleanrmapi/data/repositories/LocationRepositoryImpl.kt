package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Implementation of the [LocationRepository] interface.
 *
 * This class handles the retrieval of location data, both from the local database (Room)
 * and the remote API. It ensures caching of newly fetched locations and also resolves
 * character residents associated with a location.
 */
internal class LocationRepositoryImpl(
    private val locationApi: LocationApi,
    private val locationDAO: LocationDAO,
    private val characterApi: CharacterApi
) : LocationRepository, KoinComponent {

    /**
     * Retrieves a location by its ID.
     *
     * The method first attempts to load the location from local storage. If not found,
     * it fetches the location from the API and caches it locally before returning it.
     *
     * @param id The unique identifier of the location.
     * @return The [Location] model corresponding to the given ID.
     * @throws Exception if the location cannot be found remotely.
     */
    override suspend fun getLocationById(id: Int): Location {
        val local = locationDAO.getLocation(id)

        return local?.toModel(getResidentsFromIds(local.residentsIds))
            ?: fetchAndCacheRemoteLocation(id)
    }

    /**
     * Fetches a location from the remote API and stores it in the local database.
     *
     * @param id The unique ID of the location to fetch.
     * @return The [Location] model corresponding to the fetched data.
     * @throws Exception if the location cannot be retrieved from the API.
     */
    private suspend fun fetchAndCacheRemoteLocation(id: Int): Location {
        val remote = locationApi.getLocation(id)
            ?: throw Exception("Location $id not found")

        val dbObject = remote.toDBObject()
        locationDAO.insert(dbObject)
        return dbObject.toModel(getResidentsFromIds(dbObject.residentsIds))
    }

    /**
     * Resolves a list of [Character] residents from a comma-separated string of IDs.
     *
     * This function handles:
     * - Splitting and parsing the ID list
     * - Calling the API to fetch characters from these IDs
     * - Mapping the results into domain models
     *
     * @param ids A comma-separated string of character IDs.
     * @return A list of [Character] objects.
     */
    private suspend fun getResidentsFromIds(ids: String): List<Character> {
        val cleanIds = ids.split(",").mapNotNull { it.trim().toIntOrNull() }
        if (cleanIds.isEmpty()) return emptyList()

        return characterApi.getCharactersFromIds(cleanIds.joinToString(","))
            .map { it.toDBObject().toModel() }
    }
}