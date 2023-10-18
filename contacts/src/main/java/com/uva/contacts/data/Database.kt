package com.uva.contacts.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun contactDao(): ContactDao
}
