package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.events.KeyboardEvent
import xyz.malefic.hell.util.CollisionObject
import org.jetbrains.compose.web.css.Color as CssColor

data class PlayerPosition(
    val x: Int,
    val y: Int,
    val isMoving: Boolean,
    val direction: Direction,
)

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

data class KeyBindings(
    val up: String,
    val down: String,
    val left: String,
    val right: String
)

data class CharacterColors(
    val head: String = "#FFD590",
    val body: String = "#FF0000",
    val arms: String = "#FFD590",
    val legs: String = "#0000FF",
)

@Composable
fun Player(position: PlayerPosition, playerId: Int) {
    val colors = loadCharacterColors(playerId)

    Box(
        modifier =
            Modifier
                .position(Position.Absolute)
                .left(position.x.px)
                .top(position.y.px)
                .width(50.px)
                .height(70.px)
                .zIndex(10),
    ) {
        // Character representation
        Div(
            Modifier
                .fillMaxSize()
                .position(Position.Relative)
                .toAttrs(),
        ) {
            // Head
            Div(
                Modifier
                    .position(Position.Absolute)
                    .size(30.px)
                    .borderRadius(50.percent)
                    .backgroundColor(CssColor(colors.head))
                    .left(10.px)
                    .top(0.px)
                    .toAttrs(),
            )

            // Body
            Div(
                Modifier
                    .position(Position.Absolute)
                    .size(width = 30.px, height = 25.px)
                    .backgroundColor(CssColor(colors.body))
                    .left(10.px)
                    .top(30.px)
                    .toAttrs(),
            )

            // Arms
            if (position.direction == Direction.LEFT || position.direction == Direction.RIGHT) {
                // Left arm
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 20.px)
                        .backgroundColor(CssColor(colors.arms))
                        .left(if (position.isMoving && position.direction == Direction.LEFT) 5.px else 0.px)
                        .top(30.px)
                        .toAttrs(),
                )

                // Right arm
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 20.px)
                        .backgroundColor(CssColor(colors.arms))
                        .left(if (position.isMoving && position.direction == Direction.RIGHT) 35.px else 40.px)
                        .top(30.px)
                        .toAttrs(),
                )
            } else {
                // Arms when facing up/down
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 20.px)
                        .backgroundColor(CssColor(colors.arms))
                        .left(0.px)
                        .top(30.px)
                        .toAttrs(),
                )

                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 20.px)
                        .backgroundColor(CssColor(colors.arms))
                        .left(40.px)
                        .top(30.px)
                        .toAttrs(),
                )
            }

            // Legs
            if (position.isMoving) {
                // Left leg in motion
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 15.px)
                        .backgroundColor(CssColor(colors.legs))
                        .left(10.px)
                        .top(55.px)
                        .toAttrs(),
                )

                // Right leg in motion
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 15.px)
                        .backgroundColor(CssColor(colors.legs))
                        .left(30.px)
                        .top(55.px)
                        .toAttrs(),
                )
            } else {
                // Legs standing
                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 15.px)
                        .backgroundColor(CssColor(colors.legs))
                        .left(15.px)
                        .top(55.px)
                        .toAttrs(),
                )

                Div(
                    Modifier
                        .position(Position.Absolute)
                        .size(width = 10.px, height = 15.px)
                        .backgroundColor(CssColor(colors.legs))
                        .left(25.px)
                        .top(55.px)
                        .toAttrs(),
                )
            }
        }
    }
}

@Composable
fun rememberPlayerPosition(
    collisionObjects: List<CollisionObject>,
    initialX: Int,
    initialY: Int,
    keyBindings: KeyBindings
): PlayerPosition {
    val keysPressed = remember { mutableSetOf<String>() }
    val playerPosition = remember { mutableStateOf(PlayerPosition(initialX, initialY, false, Direction.DOWN)) }

    DisposableEffect(Unit) {
        val handleKeyDown = { event: KeyboardEvent ->
            keysPressed.add(event.key)
            Unit
        }

        val handleKeyUp = { event: KeyboardEvent ->
            keysPressed.remove(event.key)
            Unit
        }

        // Convert to EventListener to fix type issues
        val keyDownListener = { e: dynamic ->
            handleKeyDown(e as KeyboardEvent)
        }

        val keyUpListener = { e: dynamic ->
            handleKeyUp(e as KeyboardEvent)
        }

        document.addEventListener("keydown", keyDownListener)
        document.addEventListener("keyup", keyUpListener)

        val intervalId =
            window.setInterval({
                var newX = playerPosition.value.x
                var newY = playerPosition.value.y
                var direction = playerPosition.value.direction
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

                // Check for collisions
                var collides = false
                for (obj in collisionObjects) {
                    // Simple rectangle collision detection
                    if (newX < obj.x + obj.width &&
                        newX + 50 > obj.x &&
                        newY < obj.y + obj.height &&
                        newY + 70 > obj.y
                    ) {
                        collides = true
                        break
                    }
                }

                // Boundary check
                if (newX < 0) newX = 0
                if (newX > 800 - 50) newX = 800 - 50 // Assuming 800px width and 50px player width
                if (newY < 0) newY = 0
                if (newY > 600 - 70) newY = 600 - 70 // Assuming 600px height and 70px player height

                if (!collides) {
                    playerPosition.value = PlayerPosition(newX, newY, isMoving, direction)
                }
            }, 16) // 60 FPS

        onDispose {
            document.removeEventListener("keydown", keyDownListener)
            document.removeEventListener("keyup", keyUpListener)
            window.clearInterval(intervalId)
        }
    }

    return playerPosition.value
}

private fun loadCharacterColors(playerId: Int): CharacterColors {
    val defaultHeadColor = "#FFD590"
    // Use green body for player 2 for diagnostics, red for player 1 (or non-2)
    val defaultBodyColor = if (playerId == 2) "#00FF00" else "#FF0000"
    val defaultArmsColor = "#FFD590"
    val defaultLegsColor = "#0000FF"

    // Try loading player-specific colors, then generic, then defaults
    val headColor = localStorage.getItem("character_head_p$playerId") ?: localStorage.getItem("character_head") ?: defaultHeadColor
    val bodyColor = localStorage.getItem("character_body_p$playerId") ?: localStorage.getItem("character_body") ?: defaultBodyColor
    val armsColor = localStorage.getItem("character_arms_p$playerId") ?: localStorage.getItem("character_arms") ?: defaultArmsColor
    val legsColor = localStorage.getItem("character_legs_p$playerId") ?: localStorage.getItem("character_legs") ?: defaultLegsColor

    return CharacterColors(headColor, bodyColor, armsColor, legsColor)
}
