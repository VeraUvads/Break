package com.uva.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
        ),
    ],
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Long = 0,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "user_id") val userId: Long,
)
