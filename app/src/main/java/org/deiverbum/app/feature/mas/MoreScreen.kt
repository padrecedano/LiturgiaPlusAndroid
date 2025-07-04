package org.deiverbum.app.feature.mas

import LPlusIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.EUCHARISTIA_BREVIS_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_ORDINARIUM_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_ORDINARIUM_SACERDOS
import org.deiverbum.app.util.Constants.EUCHARISTIA_VERBUM_BREVIS
import org.deiverbum.app.util.Constants.EUCHARISTIA_VERBUM_EXTENSA
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_SACERDOS
import org.deiverbum.app.util.Constants.FILE_ANGELUS
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_COMMENDATIONE_MORIENTIUM
import org.deiverbum.app.util.Constants.FILE_LITANIES
import org.deiverbum.app.util.Constants.FILE_REGINA
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_ARTICULO_MORTIS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_IN_DUBIO
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_SINE_VIATICUM
import org.deiverbum.app.util.Constants.UNCTIONIS_ORDINARIUM

@ExperimentalLayoutApi
@Composable
fun MoreRoute(
    onTopicClick: (String) -> Unit,
) {
    MoreItemsMain(onTopicClick)
}


data class MoreGroup(
    val title: String,
    val icon: ImageVector,
    var isExpanded: Boolean = true,
    val subItems: List<MoreItem>
)

data class MoreItem(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val file: String
)

val baptismus = listOf(
    MoreItem(
        title = "Bautismo en peligro de muerte",
        icon = LPlusIcons.Sacramentis,
        description = "Bautismo: Peligro de muerte",
        file = FILE_BAPTISMUS
    ),
    MoreItem(
        title = "Normativa Canónica",
        icon = LPlusIcons.Iuris,
        description = "Bautismo CIC",
        file = CIC_BAPTISMUS
    )
)

val oratio = listOf(
    MoreItem(
        title = "Ángelus",
        icon = LPlusIcons.Sacramentis,
        description = "Ángelus",
        file = FILE_ANGELUS
    ),
    MoreItem(
        title = "Regina Coeli",
        icon = LPlusIcons.Iuris,
        description = "Regina Coeli",
        file = FILE_REGINA
    ),
    MoreItem(
        title = "Letanías",
        icon = LPlusIcons.Iuris,
        description = "Letanías",
        file = FILE_LITANIES
    )
)

val unctionis = listOf(
    MoreItem(
        title = "Rito Ordinario",
        icon = LPlusIcons.Sacramentis,
        description = "Unción: Rito Ordinario",
        file = UNCTIONIS_ORDINARIUM
    ),
    MoreItem(
        title = "En peligro de muerte",
        icon = LPlusIcons.Sacramentis,
        description = "Unción: Peligro de muerte",
        file = FILE_UNCTIONIS_ARTICULO_MORTIS
    ),
    MoreItem(
        title = "En duda de muerte",
        icon = LPlusIcons.Sacramentis,
        description = "Unción: En duda de muerte",
        file = FILE_UNCTIONIS_IN_DUBIO
    ),
    MoreItem(
        title = "Unción sin Viático",
        icon = LPlusIcons.Sacramentis,
        description = "Unción sin Viático",
        file = FILE_UNCTIONIS_SINE_VIATICUM
    ),
    MoreItem(
        title = "Viático fuera de la Misa",
        icon = LPlusIcons.Sacramentis,
        description = "Viático fuera de la Misa",
        file = EUCHARISTIA_VIATICUM_SACERDOS
    ),
    MoreItem(
        title = "Encomendación del alma",
        icon = LPlusIcons.Sacramentis,
        description = "Encomendación del Alma a Dios",
        file = FILE_COMMENDATIONE_MORIENTIUM
    ),

    MoreItem(
        title = "Normativa Canónica",
        icon = LPlusIcons.Iuris,
        description = "Unción CIC",
        file = CIC_UNCTIONIS
    )
)

val missae = listOf(
    MoreItem(
        title = "1.Con Celebración de Palabra extensa",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: con Celebración de Palabra",
        file = EUCHARISTIA_VERBUM_EXTENSA
    ),
    MoreItem(
        title = "2.Con Celebración de Palabra breve",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: con Celebración de Palabra breve",
        file = EUCHARISTIA_VERBUM_BREVIS
    ),
    /*
        MoreItem(
            title = "*Comunión: Rito ordinario-Sacerdote",
            icon = LPlusIcons.Sacramentis,
            description = "Comunión: Rito ordinario-Sacerdote",
            file = EUCHARISTIA_ORDINARIUM_SACERDOS
        ),
    */

    MoreItem(
        title = "3.Comunión: Rito ordinario-Ministro",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: Rito ordinario-Ministro",
        file = EUCHARISTIA_ORDINARIUM_ALTER
    ),
    MoreItem(
        title = "4.Comunión: Rito breve-Ministro",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: Rito breve-Ministro",
        file = EUCHARISTIA_BREVIS_ALTER
    ),


    MoreItem(
        title = "5.Viático por un Ministro",
        icon = LPlusIcons.Sacramentis,
        description = "Viático fuera de la Misa: Ministro",
        file = EUCHARISTIA_VIATICUM_ALTER
    ),
    MoreItem(
        title = "Comunión: Rito más breve",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: Rito más breve",
        file = "FILE_EUCHARISTIA_ALTER_BREVIS"
    ),

    MoreItem(
        title = "Comunión a los enfermos: Rito breve",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión enfermos: Breve",
        file = FILE_COMMENDATIONE_MORIENTIUM
    ),

    MoreItem(
        title = "Comunión: Rito ordinario-Sacerdote",
        icon = LPlusIcons.Sacramentis,
        description = "Comunión: Rito ordinario-Sacerdote",
        file = EUCHARISTIA_ORDINARIUM_SACERDOS
    ),

    MoreItem(
        title = "*Viático por un Sacerdote",
        icon = LPlusIcons.Sacramentis,
        description = "Viático fuera de la Misa: Sacerdote",
        file = EUCHARISTIA_VIATICUM_SACERDOS
    ),
    MoreItem(
        title = "Normativa Canónica",
        icon = LPlusIcons.Iuris,
        description = "Unción CIC",
        file = CIC_UNCTIONIS
    )
)

val groups = listOf(
    MoreGroup("Bautismo", LPlusIcons.Water, true, baptismus),
    MoreGroup("Comunión fuera de Misa", LPlusIcons.Missae, true, missae),
    MoreGroup("Unción", LPlusIcons.OilBarrel, true, unctionis),
    MoreGroup("Oraciones", LPlusIcons.OilBarrel, true, oratio)
)

@Composable
fun MoreItemsMain(
    onClick: (String) -> Unit
) {
    val items = remember {
        mutableStateListOf<MoreGroup>().apply { addAll(groups) }
    }
    val expandedStates =
        remember { mutableStateListOf(*BooleanArray(items.size) { true }.toTypedArray()) }
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        state = listState
    ) {
        itemsIndexed(items, key = { index, _ -> index }) { index, item ->
            MoreItemsGroup(
                item = item,
                isExpanded = expandedStates[index],
                onExpandedChange = { expandedStates[index] = it },
                onClick = onClick,
            )
        }
    }
}

@Composable
fun MoreItemsGroup(
    item: MoreGroup,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onClick: (String) -> Unit,
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = LPlusIcons.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier
                    .graphicsLayer(rotationZ = rotationAngle)
                    .clickable(onClick = {
                        onExpandedChange(!isExpanded)
                    }
                    ))
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                item.subItems.forEach {
                    if (it.title == "1.Con Celebración de Palabra extensa") {
                        Text("Ministro Extraordinario:\n", fontWeight = FontWeight.SemiBold)
                    }
                    HorizontalDivider()
                    MoreItemDetail(it, onClick)
                }

            }
        }
    }
}

@Composable
private fun MoreItemDetail(
    item: MoreItem,
    onTopicClick: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clickable { onTopicClick(item.description) }
    ) {
        Icon(imageVector = item.icon, contentDescription = item.description)
        Text(
            item.title,
            modifier = Modifier.weight(1f)
        )
        Icon(imageVector = LPlusIcons.ArrowForward, contentDescription = "Abrir")
    }
}


