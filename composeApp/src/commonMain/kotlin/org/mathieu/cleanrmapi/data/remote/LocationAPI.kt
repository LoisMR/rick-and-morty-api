package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse

internal class LocationApi(private val client: HttpClient) {

    /**
     * Fetches the details of a location by its ID.
     *
     * @param id The ID of the location to retrieve.
     * @return A [LocationResponse] representing the location, or null if it doesn't exist.
     * @throws Exception if the request fails or the response is not successful.
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()
}