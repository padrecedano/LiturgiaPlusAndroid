package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.UserDataDynamic


/**
 * Pantallas para las lecturas de la Misa.
 *
 * @author A. Cedano
 * @since 2024.1
 *
 * @see [Missae]
 * @see [MissaeLectionum]
 */

@Composable
fun MissaeLectionumScreen(
    data: Missae,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        if (data.lectionumList != null) {
            //No necesario, se llama en el método
            // data.lectionumList?.lectionum?.sortBy { it!!.theOrder }
            Text(text = data.lectionumList?.getComposable(rubricColor = rubricColor)!!)
        }

        //MissaeLectionum(lectionum = data, rubricColor = rubricColor)

    }

}
