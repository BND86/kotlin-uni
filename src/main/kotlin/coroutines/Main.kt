package coroutines

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.util.concurrent.TimeUnit

data class Phase(val name: String, val durationSeconds: Int)

val phases = listOf(
    Phase("Говорение", 120),
    Phase("Письмо", 60),
    Phase("Отдых", 30)
)

fun main() = runBlocking<Unit> {
    val terminal = DefaultTerminalFactory().createTerminal()
    val screen = TerminalScreen(terminal).apply { startScreen() }

    // State
    val paused = MutableStateFlow(false)
    val currentPhaseIndex = MutableStateFlow(0)
    val remainingSeconds = MutableStateFlow(phases[0].durationSeconds)
    val cycleNumber = MutableStateFlow(1)
    val totalElapsedSeconds = MutableStateFlow(0)

    val phaseChangeChannel = Channel<Unit>()

    // Time ticker
    val tickerJob = launch {
        while (isActive) {
            delay(1000)
            if (!paused.value) {
                remainingSeconds.update { it - 1 }
                totalElapsedSeconds.update { it + 1 }
                if (remainingSeconds.value == 0) {
                    phaseChangeChannel.send(Unit)
                }
            }
        }
    }

    // Phase controller
    val controllerJob = launch {
        for (unit in phaseChangeChannel) {
            currentPhaseIndex.update { (it + 1) % phases.size }
            if (currentPhaseIndex.value == 0) {
                cycleNumber.update { it + 1 }
            }
            remainingSeconds.value = phases[currentPhaseIndex.value].durationSeconds
        }
    }

    // User input
    val inputJob = launch(Dispatchers.IO) {
        while (isActive) {
            val key = screen.pollInput()
            key?.let {
                when {
                    it.character == ' ' -> paused.value = !paused.value
                    it.keyType == KeyType.Enter -> {
                        // Skip to next phase
                        remainingSeconds.value = 0
                    }
                    it.keyType == KeyType.Escape -> cancel()
                }
            }
            delay(100) // poll every 100ms
        }
    }

    // UI update
    val uiJob = launch(Dispatchers.IO) {
        while (isActive) {
            updateUI(screen, currentPhaseIndex.value, remainingSeconds.value, cycleNumber.value, totalElapsedSeconds.value, paused.value)
            delay(500) // update UI every 500ms
        }
    }

    // Cleanup on exit
    try {
        awaitCancellation()
    } finally {
        screen.stopScreen()
        tickerJob.cancel()
        controllerJob.cancel()
        inputJob.cancel()
        uiJob.cancel()
    }
}

fun updateUI(screen: Screen, phaseIndex: Int, remaining: Int, cycle: Int, totalSec: Int, isPaused: Boolean) {
    val textGraphics = screen.newTextGraphics()
    textGraphics.setBackgroundColor(TextColor.ANSI.BLACK)
    textGraphics.setForegroundColor(TextColor.ANSI.WHITE)
    screen.clear()

    val phase = phases[phaseIndex]
    val progress = ((phases.sumOf { it.durationSeconds } - phases.drop(phaseIndex).sumOf { it.durationSeconds } + (phase.durationSeconds - remaining)) / phases.sumOf { it.durationSeconds }.toDouble()) * 100

    fun formatTime(seconds: Int): String {
        val min = seconds / 60
        val sec = seconds % 60
        return "%d:%02d".format(min, sec)
    }

    textGraphics.putString(0, 0, "Текущая фаза: ${phase.name}")
    textGraphics.putString(0, 1, "Осталось времени: ${formatTime(remaining)}")
    textGraphics.putString(0, 2, "Номер цикла: $cycle")
    textGraphics.putString(0, 3, "Общий прогресс: %.1f%%".format(progress))
    textGraphics.putString(0, 4, "Общее время: ${formatTime(totalSec)}")
    if (isPaused) {
        textGraphics.setForegroundColor(TextColor.ANSI.RED)
        textGraphics.putString(0, 6, "ПАУЗА")
    } else {
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN)
        textGraphics.putString(0, 6, "РАБОТАЕТ")
    }
    textGraphics.setForegroundColor(TextColor.ANSI.YELLOW)
    textGraphics.putString(0, 8, "Пробел: Пауза/Продолжить")
    textGraphics.putString(0, 9, "Enter: Пропустить фазу")
    textGraphics.putString(0, 10, "Esc: Выход")

    screen.refresh()
}
