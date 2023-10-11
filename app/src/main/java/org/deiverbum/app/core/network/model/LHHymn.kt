package org.deiverbum.app.core.network.model

import kotlinx.serialization.Serializable
import org.deiverbum.app.core.model.data.LHHymn


/**
 * Representación de red para [LHHymn]
 */

@Serializable
data class NetworkLHHymn(
    val himnoId: Int,
    val himno: String = ""
)