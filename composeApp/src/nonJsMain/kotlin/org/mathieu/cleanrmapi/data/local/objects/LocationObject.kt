package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Represents a location entity stored in the local SQLite database (via Room).
 *
 * This object is a Room entity tailored for database persistence and reflects
 * the structure of a location as received from the remote API.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location (e.g., "Earth").
 * @property type Type of the location (e.g., "Planet", "Space Station").
 * @property dimension The dimension the location exists in (e.g., "Dimension C-137").
 * @property residentsIds A comma-separated list of resident character IDs.
 * @property created ISO 8601 timestamp representing when the location was created.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String,
    val created: String
)

/**
 * Converts a [LocationResponse] (from the remote API) into a [LocationObject]
 * suitable for local persistence via Room.
 *
 * This includes extracting resident character IDs from their URL references.
 */
internal fun LocationResponse.toDBObject() = LocationObject(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls(),
    created = created
)

/**
 * Converts a [LocationObject] from the local database into a domain-level [Location] model.
 *
 * It takes a list of fully resolved [Character] instances as residents,
 * which should be fetched separately using the IDs.
 *
 * @param residents The list of character objects that reside in this location.
 * @return A fully constructed [Location] domain model.
 */
internal fun LocationObject.toModel(residents: List<Character>) = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents
)