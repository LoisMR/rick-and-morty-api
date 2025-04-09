package org.mathieu.cleanrmapi.ui.screens.locationDetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

/**
 * ViewModel responsible for managing the UI state of the Location Details screen.
 *
 * It loads location data from the repository and handles user interactions
 * such as clicking on a resident character.
 */
class LocationDetailsViewModel : ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    // Injects the platform-independent LocationRepository
    private val locationRepository: LocationRepository by inject()

    /**
     * Initializes the screen by fetching the location with the given ID.
     *
     * On success: emits a Loaded state containing location details and residents.
     * On failure: emits an Error state with the appropriate error message.
     *
     * @param locationId The unique ID of the location to load.
     */
    fun init(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocationById(locationId) }
        ) {
            onSuccess { location ->
                updateState {
                    LocationDetailsState.Loaded(
                        name = location.name,
                        type = location.type,
                        dimension = location.dimension,
                        residents = location.residents
                    )
                }
            }

            onFailure {
                updateState { LocationDetailsState.Error(it.message ?: "Unknown error") }
            }
        }
    }

    /**
     * Handles user actions triggered from the UI.
     *
     * Currently supports navigation to a character's detail screen when a resident is selected.
     */
    fun handleAction(action: LocationDetailsAction) {
        when (action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.character.id.toString()))
        }
    }
}

/**
 * Represents the UI state for the Location Details screen.
 */
sealed interface LocationDetailsState {

    /** Indicates the screen is currently loading data. */
    object Loading : LocationDetailsState

    /** Represents an error state, with a message to display. */
    data class Error(val message: String) : LocationDetailsState

    /**
     * Represents the successful loading of location data.
     *
     * @param name The name of the location.
     * @param type The type of the location (e.g., planet, space station).
     * @param dimension The dimension in which the location exists.
     * @param residents A list of characters who reside in the location.
     */
    data class Loaded(
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<Character>
    ) : LocationDetailsState
}

/**
 * Defines user actions available on the Location Details screen.
 */
sealed interface LocationDetailsAction {

    /**
     * Triggered when the user selects a resident character from the list.
     *
     * @param character The selected character.
     */
    data class SelectedCharacter(val character: Character) : LocationDetailsAction
}