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

internal class LocationRepositoryImpl(
    private val locationApi: LocationApi,
    private val locationDAO: LocationDAO,
    private val characterApi: CharacterApi
) : LocationRepository, KoinComponent {

    override suspend fun getLocationById(id: Int): Location {
        val local = locationDAO.getLocation(id)

        return local?.toModel(getResidentsFromIds(local.residentsIds))
            ?: fetchAndCacheRemoteLocation(id)
    }

    private suspend fun fetchAndCacheRemoteLocation(id: Int): Location {
        val remote = locationApi.getLocation(id) ?: throw Exception("Location $id not found")
        val dbObject = remote.toDBObject()
        locationDAO.insert(dbObject)
        return dbObject.toModel(getResidentsFromIds(dbObject.residentsIds))
    }

    private suspend fun getResidentsFromIds(ids: String): List<Character> {
        val cleanIds = ids.split(",").mapNotNull { it.trim().toIntOrNull() }
        if (cleanIds.isEmpty()) return emptyList()
        return characterApi.getCharactersFromIds(cleanIds.joinToString(","))
            .map { it.toDBObject().toModel() }
    }
}