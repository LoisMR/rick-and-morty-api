package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

@Dao
interface LocationDAO {

    @Query("SELECT * FROM location_table WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)
}
