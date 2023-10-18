package com.uva.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uva.contacts.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val repository: Repository) : ViewModel() {

    val contacts = repository.getContacts()
    val users = repository.getUsers()
    val usersWithContacts = repository.getUserWithContacts()

    fun addContact(name: String, contact: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addContact(name, contact)
            }
        }
    }
}
