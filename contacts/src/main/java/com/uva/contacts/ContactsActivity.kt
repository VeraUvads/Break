@file:OptIn(ExperimentalMaterial3Api::class)

package com.uva.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uva.contacts.data.AppContainer
import com.uva.contacts.data.Contact
import com.uva.contacts.data.User
import com.uva.contacts.data.UserWithContact
import com.uva.contacts.ui.theme.BreakTheme

class ContactsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreakTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(
    mainViewModel: MainViewModel = viewModel(
        factory = viewModelFactory { MainViewModel(repository = AppContainer.repository) },
    ),
) {
    val contacts by mainViewModel.contacts.collectAsState(emptyList())
    val users by mainViewModel.users.collectAsState(emptyList())
    val usersWithContact by mainViewModel.usersWithContacts.collectAsState(emptyList())

    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Add", "Contacts", "Users", "ContactsWithUser")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }
        when (tabIndex) {
            0 -> AddScreen(mainViewModel)
            1 -> Users(users)
            2 -> Contacts(contacts)
            3 -> UsersWithContact(usersWithContact)
        }
    }
}

@Composable
fun AddScreen(viewModel: MainViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var name by remember { mutableStateOf("") }
        TextField(value = name, onValueChange = { name = it })

        var contact by remember { mutableStateOf("") }
        TextField(value = contact, onValueChange = { contact = it })

        Button(onClick = { viewModel.addContact(name, contact) }) {
            Text(text = "Save")
        }
    }
}

@Composable
fun Users(users: List<User>) {
    LazyColumn(content = {
        items(users) {
            Text(text = it.firstName.toString())
        }
    })
}

@Composable
fun Contacts(contacts: List<Contact>) {
    LazyColumn(content = {
        items(contacts) {
            Text(text = it.number)
        }
    })
}

@Composable
fun UsersWithContact(usersWithContact: List<UserWithContact>) {
    LazyColumn(content = {
        items(usersWithContact) {
            Text(text = it.user.firstName)
            Text(text = it.numbers.first().number)
        }
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BreakTheme {
        Greeting("Android")
    }
}
