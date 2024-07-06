package org.deiverbum.app.ui.interests

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * Stateful composable that displays the Navigation route for the Interests screen.
 *
 * @param interestsViewModel ViewModel that handles the business logic of this screen
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) state for screen snackbar host
 */
@Composable
fun InterestsRoute(
    interestsViewModel: InterestsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val tabContent = rememberTabContent(interestsViewModel)
    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }

    InterestsScreen(
        tabContent = tabContent,
        currentSection = currentSection,
        isExpandedScreen = isExpandedScreen,
        onTabChange = updateSection,
        openDrawer = openDrawer,
        snackbarHostState = snackbarHostState
    )
}