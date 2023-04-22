package org.deiverbum.app.domain.model

class TodayRequest(
    val theDate: Int?,
    val typeID: Int,
    val isNightMode: Boolean,
    val isVoiceOn: Boolean,
    val isMultipleInvitatory: Boolean
)