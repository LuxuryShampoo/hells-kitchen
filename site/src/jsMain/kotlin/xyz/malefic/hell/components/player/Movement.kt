package xyz.malefic.hell.components.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent
import xyz.malefic.hell.util.CollisionObject
import xyz.malefic.hell.util.checkCollisions

/**
 * Enum representing the possible directions a player can face.
 */
enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

/**
 * Data class representing key bindings for player movement.
 *
 * @property up Key for moving up.
 * @property down Key for moving down.
 * @property left Key for moving left.
 * @property right Key for moving right.
 */
data class KeyBindings(
    val up: String,
    val down: String,
    val left: String,
    val right: String,
)

/**
 * Data class representing the player's position and movement state.
 *
 * @property x The x-coordinate of the player.
 * @property y The y-coordinate of the player.
 * @property isMoving Whether the player is currently moving.
 * @property direction The direction the player is facing.
 */
data class PlayerPosition(
    val x: Int,
    val y: Int,
    val isMoving: Boolean,
    val direction: Direction,
)

/**
 * Composable function to track the state of keys pressed by the user.
 *
 * @return A set of strings representing the keys currently pressed.
 */
@Composable
fun rememberKeyPressState(): Set<String> {
    val keysPressed = remember { mutableSetOf<String>() }

    DisposableEffect(Unit) {
        val keyDownListener = { e: dynamic ->
            keysPressed.add((e as KeyboardEvent).key)
            Unit
        }

        val keyUpListener = { e: dynamic ->
            keysPressed.remove((e as KeyboardEvent).key)
            Unit
        }

        document.addEventListener("keydown", keyDownListener)
        document.addEventListener("keyup", keyUpListener)

        onDispose {
            document.removeEventListener("keydown", keyDownListener)
            document.removeEventListener("keyup", keyUpListener)
        }
    }

    return keysPressed
}

/**
 * Calculates the new position of the player based on the current position,
 * keys pressed, and key bindings.
 *
 * @param currentPosition The current position of the player.
 * @param keysPressed A set of keys currently pressed.
 * @param keyBindings The key bindings for movement.
 * @return The new position of the player.
 */
private fun calculateNewPosition(
    currentPosition: PlayerPosition,
    keysPressed: Set<String>,
    keyBindings: KeyBindings,
): PlayerPosition {
    var newX = currentPosition.x
    var newY = currentPosition.y
    var direction = currentPosition.direction
    var isMoving = false

    if (keysPressed.contains(keyBindings.up)) {
        newY -= 5
        direction = Direction.UP
        isMoving = true
    }
    if (keysPressed.contains(keyBindings.down)) {
        newY += 5
        direction = Direction.DOWN
        isMoving = true
    }
    if (keysPressed.contains(keyBindings.left)) {
        newX -= 5
        direction = Direction.LEFT
        isMoving = true
    }
    if (keysPressed.contains(keyBindings.right)) {
        newX += 5
        direction = Direction.RIGHT
        isMoving = true
    }

    return PlayerPosition(newX, newY, isMoving, direction)
}

/**
 * Composable function to manage and update the player's position based on
 * key presses and collision detection.
 *
 * @param collisionObjects A list of objects to check for collisions.
 * @param initialX The initial x-coordinate of the player.
 * @param initialY The initial y-coordinate of the player.
 * @param keyBindings The key bindings for movement.
 * @return The current position of the player.
 */
@Composable
fun rememberPlayerPosition(
    collisionObjects: List<CollisionObject>,
    initialX: Int,
    initialY: Int,
    keyBindings: KeyBindings,
): PlayerPosition {
    val keysPressed = rememberKeyPressState()
    val playerPosition = remember { mutableStateOf(PlayerPosition(initialX, initialY, false, Direction.DOWN)) }

    DisposableEffect(Unit) {
        val intervalId =
            window.setInterval({
                val current = playerPosition.value
                val keys = keysPressed
                var newX = current.x
                var newY = current.y
                var direction = current.direction
                var isMoving = false

                if (keys.contains(keyBindings.up)) {
                    val tryY = newY - 5
                    val testPos = current.copy(y = tryY)
                    if (!checkCollisions(testPos, collisionObjects)) {
                        newY = tryY
                    }
                    direction = Direction.UP
                    isMoving = true
                }
                if (keys.contains(keyBindings.down)) {
                    val tryY = newY + 5
                    val testPos = current.copy(y = tryY)
                    if (!checkCollisions(testPos, collisionObjects)) {
                        newY = tryY
                    }
                    direction = Direction.DOWN
                    isMoving = true
                }
                if (keys.contains(keyBindings.left)) {
                    val tryX = newX - 5
                    val testPos = current.copy(x = tryX)
                    if (!checkCollisions(testPos, collisionObjects)) {
                        newX = tryX
                    }
                    direction = Direction.LEFT
                    isMoving = true
                }
                if (keys.contains(keyBindings.right)) {
                    val tryX = newX + 5
                    val testPos = current.copy(x = tryX)
                    if (!checkCollisions(testPos, collisionObjects)) {
                        newX = tryX
                    }
                    direction = Direction.RIGHT
                    isMoving = true
                }

                val boundedX = newX.coerceIn(0, 800 - 50)
                val boundedY = newY.coerceIn(0, 500 - 70)
                playerPosition.value = PlayerPosition(boundedX, boundedY, isMoving, direction)
            }, 16)

        onDispose {
            window.clearInterval(intervalId)
        }
    }

    return playerPosition.value
}
