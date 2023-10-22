package com.riedera.events.domain.models.old

import java.time.LocalDateTime

data class Task(
    override var id: Long = 0,
    override var name: String? = null,
    var task: String,
    override var dueDateAndTime: LocalDateTime?,
    override var deadline: LocalDateTime?,
) : InfoItem()
