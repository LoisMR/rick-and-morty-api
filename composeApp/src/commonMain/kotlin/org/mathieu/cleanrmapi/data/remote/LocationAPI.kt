package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse

internal class LocationApi(private val client: HttpClient) {

    /**
     * Récupère les détails d'une location via son ID.
     *
     * @param id L'identifiant de la location à récupérer.
     * @return Un [LocationResponse] représentant la location ou null si elle n'existe pas.
     * @throws Exception si la requête échoue.
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()
}