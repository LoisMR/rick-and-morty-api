package org.mathieu.cleanrmapi.domain.locationPreview

/**
 * Represents the preview of description of an episode.
 *
 * @property id The unique identifier for the location = url.
 * @property name The name of the location.

 */
data class LocationPreview(
    val id: String,
    val name: String
)