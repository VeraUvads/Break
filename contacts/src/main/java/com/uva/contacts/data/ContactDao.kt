package com.uva.contacts.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): Flow<List<Contact>>

    @Transaction
    @Query("SELECT * FROM user WHERE name  LIKE :name")
    fun loadUserAndContact(name: String): Flow<List<UserWithContact>>

    @Transaction
    @Query("SELECT * FROM user")
    fun loadUserAndContact(): Flow<List<UserWithContact>>

    @Insert
    fun insert(contact: Contact)

    @Delete
    fun delete(user: Contact)
}
