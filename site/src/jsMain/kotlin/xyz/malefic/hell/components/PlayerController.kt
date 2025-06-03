package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import xyz.malefic.hell.pages.Point
import xyz.malefic.hell.util.CollisionObject
import xyz.malefic.hell.util.isCollision
import xyz.malefic.hell.util.setupKeyboardControls

@Composable
fun rememberPlayerPosition(
    initialPosition: Point = Point(300, 250),
    collisionObjects: List<CollisionObject>,
    bounds: Pair<Int, Int> = 770 to 470,
    speed: Int = 5,
): Point {
    var characterPosition by remember { mutableStateOf(initialPosition) }

    LaunchedEffect(Unit) {
        setupKeyboardControls { pressedKeys ->
            var dx = 0
            var dy = 0
            if ("ArrowLeft" in pressedKeys) dx -= speed
            if ("ArrowRight" in pressedKeys) dx += speed
            if ("ArrowUp" in pressedKeys) dy -= speed
            if ("ArrowDown" in pressedKeys) dy += speed
            val newX = characterPosition.x + dx
            val newY = characterPosition.y + dy
            if ((dx != 0 || dy != 0) && newX in 0..bounds.first && newY in 0..bounds.second) {
                if (!isCollision(newX, newY, collisionObjects)) {
                    characterPosition = Point(newX, newY)
                }
            }
        }
    }
    return characterPosition
}
