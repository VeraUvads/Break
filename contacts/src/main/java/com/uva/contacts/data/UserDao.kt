package com.uva.contacts.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 10")
    fun findByName(first: String): User

    @Insert
    fun insert(user: User): Long

    @Delete
    fun delete(user: User)
}
