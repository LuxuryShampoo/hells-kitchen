package xyz.malefic.hell.util

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.GeneralKind
import com.varabyte.kobweb.silk.style.toModifier
import xyz.malefic.hell.components.player.PlayerPosition

@Composable
fun CssStyle<GeneralKind>.collide(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    collisionObjects: MutableList<CollisionObject>,
) = this.toModifier().collide(x, y, width, height, collisionObjects)

fun Modifier.collide(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    collisionObjects: MutableList<CollisionObject>,
): Modifier {
    collisionObjects.add(CollisionObject(x, y, width, height))
    return this
}

data class CollisionObject(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
)

/**
 * Checks for collisions between the player and collision objects.
 *
 * @param position The current position of the player.
 * @param collisionObjects A list of objects to check for collisions.
 * @return True if a collision is detected, false otherwise.
 */
fun checkCollisions(
    position: PlayerPosition,
    collisionObjects: List<CollisionObject>,
): Boolean {
    val playerLeft = position.x.coerceIn(0, 800 - 50)
    val playerTop = position.y.coerceIn(0, 500 - 70)
    val playerRight = playerLeft + 50
    val playerBottom = playerTop + 70

    for (obj in collisionObjects) {
        val objLeft = obj.x
        val objTop = obj.y
        val objRight = obj.x + obj.width
        val objBottom = obj.y + obj.height

        if (playerLeft < objRight &&
            playerRight > objLeft &&
            playerTop < objBottom &&
            playerBottom > objTop
        ) {
            return true
        }
    }
    return false
}
