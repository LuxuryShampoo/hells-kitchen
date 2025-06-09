// ...existing imports...
import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.KeyboardEvent

@Composable
fun Kitchen1Page() {
    // Helper function to check if a player is near a station
    fun isNearStation(
        playerPos: Pair<Int, Int>,
        stationPos: Pair<Int, Int>,
    ): Boolean {
        val (px, py) = playerPos
        val (sx, sy) = stationPos
        return (px == sx && py == sy) ||
            (px == sx && py == sy - 1) ||
            (px == sx && py == sy + 1) ||
            (px == sx - 1 && py == sy) ||
            (px == sx + 1 && py == sy)
    }

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

    // Track which player is interacting with which station
    var player1Interacting by remember { mutableStateOf(false) }
    var player2Interacting by remember { mutableStateOf(false) }

    // Handle keyboard input for both players
    DisposableEffect(Unit) {
        // Using explicitly typed function references
        val keyDownListener: (KeyboardEvent) -> Unit = { event ->
            when (event.key.lowercase()) {
                "arrowup", "arrowdown", "arrowleft", "arrowright" -> {
                    player1Keys.value = player1Keys.value + event.key.lowercase()
                }
                "w", "a", "s", "d" -> {
                    player2Keys.value = player2Keys.value + event.key.lowercase()
                }
                "e" -> {
                    if (isNearStation(player1Pos, station1Pos) || isNearStation(player1Pos, station2Pos)) {
                        player1Interacting = true
                    }
                }
                "q" -> {
                    if (isNearStation(player2Pos, station1Pos) || isNearStation(player2Pos, station2Pos)) {
                        player2Interacting = true
                    }
                }
            }
        }

        val keyUpListener: (KeyboardEvent) -> Unit = { event ->
            when (event.key.lowercase()) {
                "arrowup", "arrowdown", "arrowleft", "arrowright" -> {
                    player1Keys.value = player1Keys.value - event.key.lowercase()
                }
                "w", "a", "s", "d" -> {
                    player2Keys.value = player2Keys.value - event.key.lowercase()
                }
                "e" -> {
                    player1Interacting = false
                }
                "q" -> {
                    player2Interacting = false
                }
            }
        }

        // Add event listeners using the dynamic approach
        (window.asDynamic()).addEventListener("keydown", keyDownListener)
        (window.asDynamic()).addEventListener("keyup", keyUpListener)

        // Return the dispose effect with correct cleanup
        onDispose {
            (window.asDynamic()).removeEventListener("keydown", keyDownListener)
            (window.asDynamic()).removeEventListener("keyup", keyUpListener)
        }
    }

    // Move player 1 with arrow keys
    LaunchedEffect(player1Keys.value) {
        val moveAmount = 1
        var newX = player1Pos.first
        var newY = player1Pos.second

        if (player1Keys.value.contains("arrowup")) {
            newY -= moveAmount
        }
        if (player1Keys.value.contains("arrowdown")) {
            newY += moveAmount
        }
        if (player1Keys.value.contains("arrowleft")) {
            newX -= moveAmount
        }
        if (player1Keys.value.contains("arrowright")) {
            newX += moveAmount
        }

        // Constrain to game boundaries
        newX = newX.coerceIn(0, 10)
        newY = newY.coerceIn(0, 10)

        if (newX != player1Pos.first || newY != player1Pos.second) {
            player1Pos = Pair(newX, newY)
        }
    }

    // Move player 2 with WASD
    LaunchedEffect(player2Keys.value) {
        val moveAmount = 1
        var newX = player2Pos.first
        var newY = player2Pos.second

        if (player2Keys.value.contains("w")) {
            newY -= moveAmount
        }
        if (player2Keys.value.contains("s")) {
            newY += moveAmount
        }
        if (player2Keys.value.contains("a")) {
            newX -= moveAmount
        }
        if (player2Keys.value.contains("d")) {
            newX += moveAmount
        }

        // Constrain to game boundaries
        newX = newX.coerceIn(0, 10)
        newY = newY.coerceIn(0, 10)

        if (newX != player2Pos.first || newY != player2Pos.second) {
            player2Pos = Pair(newX, newY)
        }
    }

    // Timer effect for station 1 - Now considers both player interactions
    LaunchedEffect(player1Pos, player2Pos, player1Interacting, player2Interacting, station1Done) {
        if (!station1Done &&
            (
                (isNearStation(player1Pos, station1Pos) && player1Interacting) ||
                    (isNearStation(player2Pos, station1Pos) && player2Interacting)
            )
        ) {
            val start = window.performance.now().toLong()
            while ((
                    (isNearStation(player1Pos, station1Pos) && player1Interacting) ||
                        (isNearStation(player2Pos, station1Pos) && player2Interacting)
                ) &&
                !station1Done
            ) {
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

    // Timer effect for station 2 - Now considers both player interactions
    LaunchedEffect(player1Pos, player2Pos, player1Interacting, player2Interacting, station2Done) {
        if (!station2Done &&
            (
                (isNearStation(player1Pos, station2Pos) && player1Interacting) ||
                    (isNearStation(player2Pos, station2Pos) && player2Interacting)
            )
        ) {
            val start = window.performance.now().toLong()
            while ((
                    (isNearStation(player1Pos, station2Pos) && player1Interacting) ||
                        (isNearStation(player2Pos, station2Pos) && player2Interacting)
                ) &&
                !station2Done
            ) {
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

    // Debug information
    Div {
        Text("Player Positions:")
        Div {
            Text("P1: (${player1Pos.first}, ${player1Pos.second}) - Interacting: $player1Interacting")
        }
        Div {
            Text("P2: (${player2Pos.first}, ${player2Pos.second}) - Interacting: $player2Interacting")
        }
        Div {
            Text("Near Station 1: P1=${isNearStation(player1Pos, station1Pos)}, P2=${isNearStation(player2Pos, station1Pos)}")
        }
        Div {
            Text("Near Station 2: P1=${isNearStation(player1Pos, station2Pos)}, P2=${isNearStation(player2Pos, station2Pos)}")
        }
    }

    // Add instructions for player controls
    Div {
        Text("Player 1: Arrow keys to move, E to interact when near a station")
        Text("Player 2: WASD to move, Q to interact when near a station")
    }

    // ...existing code for rendering kitchen, players, etc...
}
