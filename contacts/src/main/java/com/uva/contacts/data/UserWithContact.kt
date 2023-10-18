package com.uva.contacts.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithContact(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id",
    )
    val numbers: List<Contact>,
)
