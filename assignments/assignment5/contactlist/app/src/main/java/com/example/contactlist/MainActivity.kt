package com.example.contactlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.tooling.preview.Preview

/**
 * This project covers concepts from Chapter 8 lessons:
 * - "Lazy Column" - for creating scrollable contact lists
 * - "Handling Clicks and Selection" - for interactive contact selection
 * - "Combining LazyColumn and LazyRow" - for understanding list composition
 *
 * Students should review these lessons before starting:
 * - LazyColumn lesson for list implementation
 * - Clicks and Selection lesson for interactive behavior
 * - Combined lesson for understanding how lists work together
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Call the main composable function
                    // Add ContactListApp() here
                    ContactListApp()
                }
            }
        }
    }
}




@Composable
fun ContactListApp() {
    // TODO: Create sample contact data
    // Create a list of 25 contacts with names, emails, and phone numbers
    // Use the Contact data class provided below
    // See "Lazy Column" lesson for examples of data structures and LazyColumn usage
    val contacts = listOf(
        // TODO: Add 25Contact objects here
        // Example: Contact("John Doe", "john@example.com", "555-0101")
        Contact("John Doe", "john@example.com", "555-0101"),
        Contact("Jane Smith", "jane@example.com", "555-0102"),
        Contact("Bob Johnson", "bob@example.com", "555-0103"),
        Contact("Alice Brown", "alice@example.com", "555-0104"),
        Contact("Charlie Wilson", "charlie@example.com", "555-0105"),
        Contact("Diana Davis", "diana@example.com", "555-0106"),
        Contact("Eve Miller", "eve@example.com", "555-0107"),
        Contact("Frank Garcia", "frank@example.com", "555-0108"),
        Contact("Grace Lee", "grace@example.com", "555-0109"),
        Contact("Henry Taylor", "henry@example.com", "555-0110"),
        Contact("Ivy Chen", "ivy@example.com", "555-0111"),
        Contact("Jack Moore", "jack@example.com", "555-0112"),
        Contact("Kate Anderson", "kate@example.com", "555-0113"),
        Contact("Liam Thomas", "liam@example.com", "555-0114"),
        Contact("Mia Jackson", "mia@example.com", "555-0115"),
        Contact("Noah White", "noah@example.com", "555-0116"),
        Contact("Olivia Harris", "olivia@example.com", "555-0117"),
        Contact("Paul Martin", "paul@example.com", "555-0118"),
        Contact("Quinn Thompson", "quinn@example.com", "555-0119"),
        Contact("Rachel Young", "rachel@example.com", "555-0120"),
        Contact("Sam Walker", "sam@example.com", "555-0121"),
        Contact("Tina Allen", "tina@example.com", "555-0122"),
        Contact("Uma King", "uma@example.com", "555-0123"),
        Contact("Victor Scott", "victor@example.com", "555-0124"),
        Contact("Wendy Green", "wendy@example.com", "555-0125")
    )

    // TODO: Call the ContactList composable
    // Pass the contacts list as a parameter
    ContactList(contacts = contacts)
}

@Composable
fun ContactList(contacts: List<Contact>) {
    // TODO: Create state variable for selected contact
    // Use remember and mutableStateOf to track which contact is selected
    // Type should be Contact? (nullable Contact)
    // See "Handling Clicks and Selection" lesson for examples of state management and selection tracking
    var selectedContact by remember { mutableStateOf<Contact?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        // TODO: Add header text
        // Create a Text composable with:
        // - text = "Contact List"
        // - style = MaterialTheme.typography.headlineMedium
        // - modifier = Modifier.padding(bottom = 16.dp)
        Text(
            text = "Contact List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // TODO: Add selection status text
        // Create a Text composable that shows:
        // - "Selected: [contact name]" if a contact is selected
        // - "No contact selected" if no contact is selected
        // - style = MaterialTheme.typography.bodyLarge
        // - modifier = Modifier.padding(bottom = 24.dp)
        Text(
            text = selectedContact?.let { "Selected: ${it.name}" } ?: "No contact selected",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // TODO: Create LazyColumn for the contact list
        // Use LazyColumn with:
        // - modifier = Modifier.weight(1f)
        // - verticalArrangement = Arrangement.spacedBy(8.dp)
        // - items(contacts) to iterate through the contacts
        // - Call ContactItem for each contact with proper parameters
        // See "Lazy Column" lesson for complete LazyColumn implementation examples
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) { contact ->
                ContactItem(
                    contact = contact,
                    isSelected = selectedContact == contact,
                    onClick = { selectedContact = contact }
                )
            }
        }

        // TODO: Add clear selection button
        // Only show this button when a contact is selected
        // Use Button with:
        // - onClick to clear the selection
        // - modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        // - Text("Clear Selection")
        // See "Handling Clicks and Selection" lesson for examples of conditional UI and button interactions
        if (selectedContact != null) {
            Button(
                onClick = { selectedContact = null },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Clear Selection")
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Create contact avatar (colored circle with initials)
            // Use Box with:
            // - modifier = Modifier.size(50.dp)
            // - background with conditional color (primary when selected, secondary when not)
            // - shape = CircleShape
            // - contentAlignment = Alignment.Center
            // - Text with contact initials (first letter of each name)
            // See "Handling Clicks and Selection" lesson for examples of conditional styling based on selection state
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.split(" ").map { it.first() }.joinToString(""),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // TODO: Create contact information column
            // Use Column with:
            // - modifier = Modifier.weight(1f)
            // - Text for name (titleMedium, bold, conditional color)
            // - Text for email (bodyMedium, conditional color)
            // - Text for phone (bodySmall, conditional color)
            // See "Handling Clicks and Selection" lesson for examples of conditional text styling and layout
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }

            // TODO: Add selection indicator
            // Only show when contact is selected
            // Use Icon with:
            // - imageVector = Icons.Default.Check
            // - contentDescription = "Selected"
            // - tint = MaterialTheme.colorScheme.primary
            // - modifier = Modifier.size(24.dp)
            // See "Handling Clicks and Selection" lesson for examples of conditional UI elements and visual feedback
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Data class for contact information
data class Contact(
    val name: String,
    val email: String,
    val phone: String
)

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactListAppPreview() {
    ContactListApp()
}
// 1. Why should you use LazyColumn instead of Column for the contact list, and what runtime
// benefits does it provide for long lists?
//
// 2. When should you use rememberSaveable instead of remember for tracking a selected contact, and how does
// it help preserve the selected contact during a configuration change such as screen rotation?
//
// 3. Explain how Modifier order (e.g., padding before vs. after background) changes layout and appearance;
// give one practical reason this matters for the contact item card.
//
// 4. How would you implement multi-selection (data structure, toggle behavior, and UI affordances such as
// a selection count and bulk actions)?
//
// 5. Show me how selection state flows through your app. Point to where a contact is clicked, then trace how
// that changes the selectedContact state, and finally how it updates the UI colors and selection indicator.
// What would break if you moved the state variable to a different function?