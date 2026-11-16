package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ContactViewModel by viewModels {
            ContactViewModel.provideFactory(application)
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen(viewModel)
                }
            }
        }
    }
}
//1. Having the list update as you type the letters in the search box was not discussed
// in class. Go through the code and explain how you implemented that functionality.
//
//2. Explain the purpose of a Data Access Object (DAO) in this type of application.
// What specific operations would you expect a Contact DAO to perform?
//
//3. Describe the primary responsibilities of the ContactViewModel. How does it facilitate
// communication between the UI and the data layer, and what problem does it solve in the
// context of Android lifecycles?
//
//4. How does the application handle and observe changes in the list of contacts or the
// search query to keep the UI updated? What pattern or mechanism is typically used for this
// in modern Android UI development?
//
//5. Briefly describe the overall architecture of this application, mentioning the key layers
// (UI, ViewModel, Repository, Database) and how they interact. Why is this architectural
// pattern beneficial?