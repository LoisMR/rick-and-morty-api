package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.ui.core.composables.Avatar
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.IconWithImage
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor
import org.mathieu.cleanrmapi.ui.screens.locationDetails.LocationDetailsAction
import org.mathieu.cleanrmapi.ui.screens.locationDetails.LocationDetailsState
import org.mathieu.cleanrmapi.ui.screens.locationDetails.LocationDetailsViewModel

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(Unit) {
            viewModel.init(locationId = id)
        }

        Content(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
private fun Content(
    state: LocationDetailsState,
    onClickBack: () -> Unit,
    onAction: (LocationDetailsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        BackArrow(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            onClick = onClickBack
        )

        Crossfade(targetState = state) {
            when (it) {
                is LocationDetailsState.Loading -> Text("Chargement...")
                is LocationDetailsState.Error -> Text("Erreur : ${it.message}")
                is LocationDetailsState.Loaded -> LocationDetailsContent(
                    state = it,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun LocationDetailsContent(
    state: LocationDetailsState.Loaded,
    onAction: (LocationDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 72.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoBox(label = "Nom", value = state.name)
            InfoBox(label = "Type", value = state.type)
            InfoBox(label = "Dimension", value = state.dimension)
        }

        Text(
            text = "Résidents",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.residents) { character ->
                CharacterCard(
                    character = character,
                    onClick = { onAction(LocationDetailsAction.SelectedCharacter(character)) }
                )
            }
        }
    }
}

@Composable
private fun InfoBox(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "$label : $value",
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun CharacterCard(
    character: Character,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(SurfaceColor, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = character.name, fontSize = 16.sp)
            Text(text = character.species, fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconWithImage(
            imageVector = Icons.Rounded.Person,
            text = character.type.takeIf { it.isNotBlank() } ?: "Unknown"
        )
    }
}