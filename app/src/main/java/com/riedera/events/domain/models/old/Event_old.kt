package com.riedera.events.domain.models.old

import java.time.LocalDateTime

data class Event_old(
    override var id: Long = 0,
    override var name: String? = null,
    var event: String,
    override var dueDateAndTime: LocalDateTime?
) : InfoItem()
