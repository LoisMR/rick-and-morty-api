package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds

/**
 * Represents a location entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a location from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type The type of the location.
 * @property dimension The dimension of the location.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
data class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String,
    val created: String
)
