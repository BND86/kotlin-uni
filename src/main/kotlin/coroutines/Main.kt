package coroutines

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlin.collections.drop
import kotlin.collections.sumOf

data class Phase(val name: String, val durationSeconds: Int)

data class UIState(
    val phaseName: String,
    val remainingFormatted: String,
    val cycle: Int,
    val progressPercent: Double,
    val totalFormatted: String,
    val isPaused: Boolean
)

val phases = listOf(
    Phase("Говорение", 5),
    Phase("Письмо", 60),
    Phase("Отдых", 30)
)

fun main() = runBlocking {
    val terminal = DefaultTerminalFactory().createTerminal()
    val screen = TerminalScreen(terminal).apply { startScreen() }

    val totalDurationSeconds = phases.sumOf { it.durationSeconds }

    val paused = MutableStateFlow(false)
    val currentPhaseIndex = MutableStateFlow(0)
    val remainingSeconds = MutableStateFlow(phases[0].durationSeconds)
    val cycleNumber = MutableStateFlow(1)
    val totalElapsedSeconds = MutableStateFlow(0)

    val phaseChangeChannel = Channel<Unit>()

    val scope = CoroutineScope(coroutineContext + SupervisorJob())

    val uiStateFlow = combine(
        currentPhaseIndex,
        remainingSeconds,
        cycleNumber,
        totalElapsedSeconds,
        paused
    ) { phaseIndex, remainingSeconds, cycleNumber, totalElapsedSeconds, paused ->
        val phase = phases[phaseIndex]
        val elapsedInCycle = (totalDurationSeconds - phases.drop(phaseIndex).sumOf { it.durationSeconds } + (phase.durationSeconds - remainingSeconds))
        val progress = (elapsedInCycle.toDouble() / totalDurationSeconds) * 100

        UIState(
            phaseName = phase.name,
            remainingFormatted = formatTime(remainingSeconds),
            cycle = cycleNumber,
            progressPercent = progress,
            totalFormatted = formatTime(totalElapsedSeconds),
            isPaused = paused
        )
    }

    // Ticker coroutine
    scope.launch {
        while (isActive) {
            delay(1000)
            if (!paused.value) {
                remainingSeconds.update { it - 1 }
                totalElapsedSeconds.update { it + 1 }
                if (remainingSeconds.value == 0) {phaseChangeChannel.send(Unit)}
            }
        }
    }

    // Phase controller
    scope.launch {
        phaseChangeChannel.consumeEach {
            currentPhaseIndex.update { (it + 1) % phases.size }
            if (currentPhaseIndex.value == 0) cycleNumber.update { it + 1 }
            remainingSeconds.value = phases[currentPhaseIndex.value].durationSeconds
        }
    }

    // User input
    scope.launch {
        while (isActive) {
            screen.pollInput()?.let {
                when {
                    it.character == ' ' -> paused.value = !paused.value
                    it.keyType == KeyType.Enter -> phaseChangeChannel.send(Unit)
                    it.keyType == KeyType.Escape -> scope.cancel()
                }
            }
            delay(100)
        }
    }

    // UI update
    scope.launch {
        uiStateFlow.collect { state ->
            updateUI(screen, state)
        }
    }

    try {
        scope.coroutineContext.job.join()
    } finally {
        screen.stopScreen()
        println("PROGRAM FINISHED")
    }
}

fun formatTime(seconds: Int): String {
    return "%d:%02d".format(seconds / 60, seconds % 60)
}

fun updateUI(screen: Screen, state: UIState) {
    val textGraphics = screen.newTextGraphics()
    textGraphics.setBackgroundColor(TextColor.ANSI.BLACK)
    textGraphics.setForegroundColor(TextColor.ANSI.WHITE)
    screen.clear()

    textGraphics.putString(0, 0, "Текущая фаза: ${state.phaseName}")
    textGraphics.putString(0, 1, "Осталось времени: ${state.remainingFormatted}")
    textGraphics.putString(0, 2, "Номер цикла: ${state.cycle}")
    textGraphics.putString(0, 3, "Общий прогресс: %.1f%%".format(state.progressPercent))
    textGraphics.putString(0, 4, "Общее время: ${state.totalFormatted}")
    if (state.isPaused) {
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
