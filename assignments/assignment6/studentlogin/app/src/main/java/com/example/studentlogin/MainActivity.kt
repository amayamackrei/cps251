package com.example.studentlogin

// Core Android imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation imports
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * This project covers concepts from Chapter 9 lessons:
 * - "Introduction to Navigation" - for setting up basic navigation structure
 * - "Passing Arguments" - for passing user data between screens
 * - "Managing the Back Stack" - for controlling navigation flow
 * - "Navigation UI Components" - for creating user interface elements
 * - "Organizing Navigation with Multiple Files" - for project structure
 *
 * Students should review these lessons before starting:
 * - Introduction to Navigation lesson for basic navigation setup
 * - Passing Arguments lesson for data transfer between screens
 * - Managing the Back Stack lesson for navigation control
 * - Navigation UI Components lesson for UI design
 * - Organizing Navigation with Multiple Files lesson for project organization
 */

/**
 * MainActivity is the entry point of the application.
 * It sets up the basic window configuration and initializes the Compose UI with navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure the window to use light status bar icons
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Set up the Compose UI with navigation
        setContent {
            MaterialTheme {
                Surface {
                    // TODO: Call the main navigation app function here
                    // Reference: Introduction to Navigation lesson
                    LoginApp()
                }
            }
        }
    }
}

/**
 * Main navigation app that handles the login flow
 */
@Composable
fun LoginApp() {
    // TODO: Create a navigation controller using rememberNavController()
    // Reference: Introduction to Navigation lesson
    val navController = rememberNavController()

    // TODO: Set up NavHost with startDestination = "login"
    // Reference: Introduction to Navigation lesson
    NavHost(navController = navController, startDestination = "login") {

        // TODO: Add composable route for "login" screen
        // Reference: Introduction to Navigation lesson
        composable("login") {
            LoginScreen(onLoginSuccess = { name ->
                navController.navigate("welcome/$name") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }

        // TODO: Add composable route for "welcome/{userName}" with navigation arguments
        // Reference: Passing Arguments lesson
        composable(
            route = "welcome/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            WelcomeScreen(
                userName = userName,
                onViewProfile = { navController.navigate("profile/$userName") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("welcome/{userName}") { inclusive = true }
                    }
                }
            )
        }

        // TODO: Add composable route for "profile/{userName}" with navigation arguments
        // Reference: Passing Arguments lesson
        composable(
            route = "profile/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            ProfileScreen(
                userName = userName,
                onBackToWelcome = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Login screen with validation using Chapter 7 concepts
 */
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    // TODO: Create state variables for form fields (name, email, password)
    // Reference: Chapter 7 - Managing Input State lesson
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // TODO: Create state variables for validation errors (nameError, emailError, validEmailBool, passwordError)
    // Reference: Chapter 7 - Validation lesson
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var validEmailBool by remember { mutableStateOf(true) }
    var passwordError by remember { mutableStateOf(false) }

    // TODO: Create state variable for password visibility (passwordVisible)
    // Reference: Chapter 7 - Text Fields lesson
    var passwordVisible by remember { mutableStateOf(false) }

    // TODO: Define hardcoded credentials (validEmail = "student@wccnet.edu", validPassword = "password123")
    val validEmail = "student@wccnet.edu"
    val validPassword = "password123"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Add title text "Student Login" with headlineLarge style and bold font weight
        // Reference: Navigation UI Components lesson
        Text(
            text = "Student Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // TODO: Create a Card with elevation for the login form
        // Reference: Navigation UI Components lesson
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // TODO: Create OutlinedTextField for name input with:
                // - leadingIcon using Icons.Default.Person
                // - isError state
                // - supportingText for error messages
                // - keyboardOptions with ImeAction.Next
                // Reference: Chapter 7 - Text Fields lesson
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = false
                    },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    isError = nameError,
                    supportingText = {
                        if (nameError) {
                            Text("Please enter your name")
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                // TODO: Create OutlinedTextField for email input with:
                // - leadingIcon using Icons.Default.Email
                // - isError state
                // - supportingText for error messages (both incorrect email format and incorrect email)
                // - keyboardOptions with KeyboardType.Email and ImeAction.Next
                // Reference: Chapter 7 - Text Fields lesson
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !isValidEmail(it)
                        validEmailBool = true
                    },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    isError = emailError || !validEmailBool,
                    supportingText = {
                        if (emailError) {
                            Text("Please enter a valid email")
                        } else if (!validEmailBool) {
                            Text("Please enter the correct email")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // TODO: Create OutlinedTextField for password input with:
                // - leadingIcon using Icons.Default.Lock
                // - trailingIcon using TextButton for password visibility toggle
                // - visualTransformation based on passwordVisible state
                // - isError state
                // - supportingText for error messages
                // - keyboardOptions with KeyboardType.Password and ImeAction.Done
                // Reference: Chapter 7 - Text Fields lesson
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = false
                    },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(if (passwordVisible) "Hide" else "Show")
                        }
                    },
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text("Please enter the correct password")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // TODO: Create login Button with onClick validation logic:
                // - Check if name is empty, set nameError if true
                // - Check if email is valid using isValidEmail function, set emailError if false
                // - Check if email matches the validEmail, set validEmailBool if false.
                // - Check if password matches validPassword, set passwordError if false
                // - Call onLoginSuccess(name.trim()) if no errors
                // Reference: Chapter 7 - Validation lesson
                Button(
                    onClick = {
                        nameError = name.isBlank()
                        emailError = !isValidEmail(email)
                        validEmailBool = email == validEmail
                        passwordError = password != validPassword
                        if (!nameError && !emailError && validEmailBool && !passwordError) {
                            onLoginSuccess(name.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                // TODO: Add demo credentials hint text showing validEmail and validPassword
                Text(
                    text = "Demo: $validEmail / $validPassword",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/**
 * Welcome screen that displays after successful login
 */
@Composable
fun WelcomeScreen(
    userName: String,
    onViewProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Add welcome message "Welcome!" with headlineLarge style and bold font weight
        // Reference: Navigation UI Components lesson
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // TODO: Add personalized greeting "Hello, $userName!" with headlineMedium style
        // Reference: Passing Arguments lesson
        Text(
            text = "Hello, $userName!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // TODO: Create "View Profile" button that calls onViewProfile
        // Reference: Navigation UI Components lesson
        Button(
            onClick = onViewProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Profile")
        }

        // TODO: Create "Logout" button that calls onLogout
        // Reference: Navigation UI Components lesson
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Logout")
        }
    }
}

/**
 * Profile screen showing user information
 */
@Composable
fun ProfileScreen(
    userName: String,
    onBackToWelcome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Add header "User Profile" with headlineLarge style and bold font weight
        // Reference: Navigation UI Components lesson
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // TODO: Create a Card to display profile information
        // Reference: Navigation UI Components lesson
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // TODO: Add ProfileRow calls for: Name, Email, Student ID, Major, Year
                // Reference: Organizing Navigation with Multiple Files lesson
                ProfileRow("Name", userName)
                ProfileRow("Email", "student@college.edu")
                ProfileRow("Student ID", "2024001")
                ProfileRow("Major", "Computer Science")
                ProfileRow("Year", "Freshman")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // TODO: Create "Back to Welcome" button that calls onBackToWelcome
        // Reference: Managing the Back Stack lesson
        Button(
            onClick = onBackToWelcome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Welcome")
        }
    }
}

/**
 * Helper composable for profile information rows
 */
@Composable
fun ProfileRow(label: String, value: String) {
    // TODO: Create a Row with:
    // - label text with bodyLarge style and medium font weight
    // - value text with bodyLarge style
    // - horizontalArrangement = Arrangement.SpaceBetween
    // Reference: Navigation UI Components lesson
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * Email validation function using regex (Chapter 7: Regular Expressions)
 */
fun isValidEmail(email: String): Boolean {
    // TODO: Implement email validation using regex pattern
    // Pattern: "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    // Reference: Chapter 7 - Regular Expressions lesson
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex())
}

/**
 * Preview function for the login screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

/**
 * Preview function for the welcome screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            userName = "John Doe",
            onViewProfile = {},
            onLogout = {}
        )
    }
}

/**
 * Preview function for the profile screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            userName = "John Doe",
            onBackToWelcome = {}
        )
    }
}

//1. The LoginApp composable uses NavHost and rememberNavController(). Explain the primary purpose
// of NavHost and NavController in defining the overall navigation structure of an Android Compose
// application.
//
//2. The LoginApp defines routes like "welcome/{userName}" and "profile/{userName}". Explain the
// mechanism used to pass the userName data from the LoginScreen to WelcomeScreen and ProfileScreen.
// Specifically, reference how arguments are defined in the route pattern and retrieved in the target
// composable as discussed in the "Passing Arguments" lesson.
//
//3. The solution you are to create breaks down the UI into LoginScreen, WelcomeScreen, and
// ProfileScreen. Discuss the advantages of organizing UI into distinct composable functions
// and potentially separate files for improved maintainability and readability, even if this
// specific project keeps them in one file.
//
//4. For the email field, the email is either in the wrong format, is the incorrect email, or is
// valid and correct. Show the code where you programmed the logic that determines which error
// message to display based on what is wrong.
//
//5. Although Top App Bar and Bottom Navigation Bar are discussed in the navigation UI lesson,
// this solution primarily uses buttons for navigation within the content area. Discuss a scenario
// in a larger application where you might choose to implement a Bottom Navigation Bar as the primary
// navigation instead of simple buttons, and what benefits it would offer.