package com.uva.contacts.data

import kotlinx.coroutines.flow.Flow

class Repository(val database: AppDatabase) {
    fun addContact(name: String, number: String) {
        val userId = database.userDao().insert(User(firstName = name))
        database.contactDao().insert(Contact(number = number, userId = userId))
    }

    fun getContacts(): Flow<List<Contact>> {
        return database.contactDao().getAll()
    }

    fun getUsers(): Flow<List<User>> {
        return database.userDao().getAll()
    }

    fun getUserWithContacts(): Flow<List<UserWithContact>> {
        return database.contactDao().loadUserAndContact()
    }
}
