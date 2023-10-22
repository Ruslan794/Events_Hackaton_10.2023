package com.riedera.events.domain.models.old

import java.time.LocalDateTime

open class InfoItem {

    val dateOfCreation: LocalDateTime = LocalDateTime.now()
    open val id: Long = 0
    open var name: String? = null
    open var dueDateAndTime: LocalDateTime? = null
    open var deadline: LocalDateTime? = null

}