package com.example.monstergame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BattleScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun BattleScreen(viewModel: MainViewModel) {
    BattleContent(
        playerHealth = viewModel.playerHealth,
        monsterHealth = viewModel.monsterHealth,
        message = viewModel.message,
        isMonsterTurn = viewModel.isMonsterTurn,
        isGameOver = viewModel.isGameOver,
        onAttack = { viewModel.attack() },
        onHeal = { viewModel.heal() },
        onRestart = { viewModel.restart() }
    )
}

@Composable
fun BattleContent(
    playerHealth: Int,
    monsterHealth: Int,
    message: String,
    isMonsterTurn: Boolean,
    isGameOver: Boolean,
    onAttack: () -> Unit,
    onHeal: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Fight the Monster",
            style = MaterialTheme.typography.headlineMedium
        )

        HealthSection(name = "Monster", health = monsterHealth)
        HealthSection(name = "Player", health = playerHealth)

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onAttack,
                enabled = !isMonsterTurn && !isGameOver,
                modifier = Modifier.weight(1f)
            ) {
                Text("Attack")
            }
            Button(
                onClick = onHeal,
                enabled = !isMonsterTurn && !isGameOver && playerHealth < 100,
                modifier = Modifier.weight(1f)
            ) {
                Text("Heal")
            }
        }

        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart")
        }
    }
}

@Composable
fun HealthSection(name: String, health: Int) {
    var previousHealth by remember { mutableIntStateOf(health) }
    var flash by remember { mutableStateOf(false) }

    LaunchedEffect(health) {
        if (health < previousHealth) {
            flash = true
            delay(250)
            flash = false
        }
        previousHealth = health
    }

    val fraction = health / 100f

    val animatedProgress by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    val barColor by animateColorAsState(
        targetValue = when {
            fraction > 0.5f -> Color(0xFF4CAF50)
            fraction > 0.25f -> Color(0xFFFF9800)
            else -> Color(0xFFF44336)
        },
        animationSpec = tween(durationMillis = 500),
        label = "barColor"
    )

    val flashColor by animateColorAsState(
        targetValue = if (flash) MaterialTheme.colorScheme.errorContainer else Color.Transparent,
        animationSpec = tween(durationMillis = 150),
        label = "flash"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(flashColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "$name: $health / 100",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            color = barColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
    }
}