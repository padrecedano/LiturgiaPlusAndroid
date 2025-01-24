package org.deiverbum.app.core.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.UserDataDynamic

/**
 * Pantalla para los Santos.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [Alteri.Sancti]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 */
@ExperimentalMaterial3Api
@Composable
fun SanctiiScreen(
    data: Alteri.Sancti,
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    //Text(data.sanctus.forView.toString())

    TextZoomable(
        onTap = onTap, text = buildAnnotatedString { append(data.sanctus.forView) }
    )

}
