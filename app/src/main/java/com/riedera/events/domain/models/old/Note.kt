package com.riedera.events.domain.models.old

import java.time.LocalDate


data class Note(
    override var id: Long = 0,
    override var name: String? = null,
    var note: String,
    var shownOnHomeScreenLastTime: LocalDate? = null
) : InfoItem()
