package com.example.monstergame
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$name: $health / 100",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { health / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
    }
}