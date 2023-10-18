package com.uva.contacts.data

import androidx.room.Room
import com.uva.contacts.App

object AppContainer {

    val db = Room.databaseBuilder(
        App.appContext,
        AppDatabase::class.java,
        "database-name",
    ).build()

    val repository by lazy {
        Repository(db)
    }
}
