package org.deiverbum.app.core.network.model

import kotlinx.serialization.Serializable
import org.deiverbum.app.core.model.data.LHHymnNew


/**
 * Representación de red para [LHHymnNew]
 */

@Serializable
data class NetworkLHHymn(
    val himnoId: Int,
    val himno: String = ""
)