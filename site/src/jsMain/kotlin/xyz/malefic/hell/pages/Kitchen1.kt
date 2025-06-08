// ...existing imports...
import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun Kitchen1Page() {
    // ...existing code...

    val station1Pos = Pair(3, 2)
    val station2Pos = Pair(7, 2)
    val requiredTime = 2000L // milliseconds to complete a task

    var player1Pos by remember { mutableStateOf(Pair(0, 0)) }
    var player2Pos by remember { mutableStateOf(Pair(1, 0)) }
    var station1Done by remember { mutableStateOf(false) }
    var station2Done by remember { mutableStateOf(false) }
    var station1Timer by remember { mutableStateOf(0L) }
    var station2Timer by remember { mutableStateOf(0L) }
    var levelPassed by remember { mutableStateOf(false) }

    // Add key event handling for both players
    val player1Keys = remember { mutableStateOf(setOf<String>()) }
    val player2Keys = remember { mutableStateOf(setOf<String>()) }

    DisposableEffect(Unit) {
        val listener: (org.w3c.dom.events.KeyboardEvent) -> Unit = { event ->
            when (event.type) {
                "keydown" -> {
                    when (event.key.lowercase()) {
                        "arrowup", "arrowdown", "arrowleft", "arrowright" -> {
                            player1Keys.value = player1Keys.value + event.key.lowercase()
                        }
                        "w", "a", "s", "d" -> {
                            player2Keys.value = player2Keys.value + event.key.lowercase()
                        }
                    }
                }
                "keyup" -> {
                    when (event.key.lowercase()) {
                        "arrowup", "arrowdown", "arrowleft", "arrowright" -> {
                            player1Keys.value = player1Keys.value - event.key.lowercase()
                        }
                        "w", "a", "s", "d" -> {
                            player2Keys.value = player2Keys.value - event.key.lowercase()
                        }
                    }
                }
            }
        }
        window.addEventListener("keydown", listener)
        window.addEventListener("keyup", listener)
        // Correct cleanup for Compose for Web DisposableEffect
        return@DisposableEffect onDispose {
            window.asDynamic().removeEventListener("keydown", listener)
            window.asDynamic().removeEventListener("keyup", listener)
        }
    }

    // Move player 1 with arrow keys
    LaunchedEffect(player1Keys.value) {
        val (x, y) = player1Pos
        var newX = x
        var newY = y
        if ("arrowup" in player1Keys.value) newY--
        if ("arrowdown" in player1Keys.value) newY++
        if ("arrowleft" in player1Keys.value) newX--
        if ("arrowright" in player1Keys.value) newX++
        if (newX != x || newY != y) {
            player1Pos = Pair(newX.coerceIn(0, 10), newY.coerceIn(0, 10))
        }
    }

    // Move player 2 with WASD
    LaunchedEffect(player2Keys.value) {
        val (x, y) = player2Pos
        var newX = x
        var newY = y
        if ("w" in player2Keys.value) newY--
        if ("s" in player2Keys.value) newY++
        if ("a" in player2Keys.value) newX--
        if ("d" in player2Keys.value) newX++
        if (newX != x || newY != y) {
            player2Pos = Pair(newX.coerceIn(0, 10), newY.coerceIn(0, 10))
        }
    }

    // Timer effect for station 1
    LaunchedEffect(player1Pos, player2Pos, station1Done) {
        if (!station1Done && (player1Pos == station1Pos || player2Pos == station1Pos)) {
            val start = window.performance.now().toLong()
            while ((player1Pos == station1Pos || player2Pos == station1Pos) && !station1Done) {
                delay(100)
                val elapsed = window.performance.now().toLong() - start
                station1Timer = elapsed
                if (elapsed >= requiredTime) {
                    station1Done = true
                    break
                }
            }
            station1Timer = 0L
        }
    }

    // Timer effect for station 2
    LaunchedEffect(player1Pos, player2Pos, station2Done) {
        if (!station2Done && (player1Pos == station2Pos || player2Pos == station2Pos)) {
            val start = window.performance.now().toLong()
            while ((player1Pos == station2Pos || player2Pos == station2Pos) && !station2Done) {
                delay(100)
                val elapsed = window.performance.now().toLong() - start
                station2Timer = elapsed
                if (elapsed >= requiredTime) {
                    station2Done = true
                    break
                }
            }
            station2Timer = 0L
        }
    }

    // Check for level completion
    LaunchedEffect(station1Done, station2Done) {
        if (station1Done && station2Done) {
            levelPassed = true
        }
    }

    // ...existing code...

    // Render tasks
    Div {
        Text("Tasks:")
        Div {
            Text(
                if (station1Done) "✔ " else "• ",
            )
            Text("Cook at Station 1")
        }
        Div {
            Text(
                if (station2Done) "✔ " else "• ",
            )
            Text("Cook at Station 2")
        }
    }

    // Render level passed message
    if (levelPassed) {
        Div {
            Text("Level Complete! 🎉")
        }
    }

    // Example rendering for both players:
    Div {
        Span({ style { property("color", "blue") } }) { Text("P1: (${player1Pos.first}, ${player1Pos.second})") }
        Span({
            style {
                property("color", "red")
                property("margin-left", "16px")
            }
        }) { Text("P2: (${player2Pos.first}, ${player2Pos.second})") }
    }
    // ...existing code for rendering kitchen, players, etc...
}
