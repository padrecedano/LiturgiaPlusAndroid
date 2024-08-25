package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import org.deiverbum.app.core.designsystem.component.TextZoomable
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

@ExperimentalMaterial3Api
@Composable
fun MissaeLectionumScreen(
    data: Missae,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int,
    onTap: (Offset) -> Unit
) {

    val rubricColor = getRubricColor(userData = userData)
    //TextToSpeechScreen(data.forRead())


    Column {
        val onTap = { point: Offset -> }
        if (data.lectionumList != null) {
            //No necesario, se llama en el método
            // data.lectionumList?.lectionum?.sortBy { it!!.theOrder }
            //TextSample(text = data.lectionumList?.getComposable(rubricColor = rubricColor)!!,onTap=onTap)
            //Text(text = data.lectionumList.lectionum.)
            //data.lectionumList!!.sort()
            TextZoomable(
                onTap = onTap,
                text = data.lectionumList?.getComposable(rubricColor = rubricColor)!!
            )

        }

        //MissaeLectionum(lectionum = data, rubricColor = rubricColor)

    }
}


