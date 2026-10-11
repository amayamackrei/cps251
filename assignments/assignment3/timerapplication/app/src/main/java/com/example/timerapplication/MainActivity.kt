package com.example.timerapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * This project covers concepts from Chapter 6 lessons:
 * - "Understanding State in Compose" - for state management and updates
 * - "Stateless and Stateful Composables" - for component design patterns
 * - "Launched Effect" - for side effects and timers
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp() {
    var isRunning by remember { mutableStateOf(false) }
    var sessionLength by remember { mutableStateOf(25) }
    var timeRemaining by remember { mutableStateOf(sessionLength * 60) }
    var completedSessions by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerDisplay(
            timeRemaining = timeRemaining,
            sessionLength = sessionLength
        )

        Spacer(modifier = Modifier.height(32.dp))

        TimerControls(
            isRunning = isRunning,
            onToggleTimer = {
                if (isRunning) {
                    isRunning = false
                    timeRemaining = sessionLength * 60
                } else {
                    isRunning = true
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        SessionSettings(
            sessionLength = sessionLength,
            onSessionLengthChange = { newLength ->
                sessionLength = newLength
                timeRemaining = newLength * 60
                isRunning = false
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Completed Sessions: $completedSessions",
            fontSize = 18.sp
        )
    }

    LaunchedEffect(isRunning) {
        while (isRunning && timeRemaining > 0) {
            delay(1000)
            timeRemaining--
        }
        if (isRunning && timeRemaining == 0) {
            completedSessions++
            isRunning = false
            timeRemaining = sessionLength * 60
        }
    }
}

@Composable
fun TimerDisplay(
    timeRemaining: Int,
    sessionLength: Int
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = 1f - (timeRemaining.toFloat() / (sessionLength * 60))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Study Timer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "%02d:%02d".format(minutes, seconds),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${(progress * 100).toInt()}% Complete",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onToggleTimer: () -> Unit
) {
    Button(onClick = onToggleTimer) {
        Text(if (isRunning) "Reset" else "Start")
    }
}

@Composable
fun SessionSettings(
    sessionLength: Int,
    onSessionLengthChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Session Length: $sessionLength minutes",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(5, 15, 25, 45).forEach { minutes ->
                val selected = minutes == sessionLength
                Button(
                    onClick = { onSessionLengthChange(minutes) },
                    modifier = Modifier.width(70.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    colors = if (selected) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                ) {
                    Text(text = "${minutes}m", maxLines = 1)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyTimerPreview() {
    StudyTimerApp()
}

//1. What are the advantages of using remember and mutableStateOf for managing state in a Compose
// application, and how would you apply them to manage the isRunning and timeRemaining states in
// this project?
//
//2. What is the difference between stateless and stateful composables, and why is it important to keep
// TimerDisplay stateless in this project?
//
//3. How would you implement a countdown timer using LaunchedEffect in Compose, and what considerations
// should you make to ensure the timer stops when it reaches zero?
//
//4. Why is it beneficial to separate the TimerControls and SessionSettings into their own composables,
// and how does this improve the reusability and readability of the code?
//
//5. What is the role of LaunchedEffect in managing side effects in Compose, and how would you use it
// to ensure the timer updates every second only when isRunning is true?