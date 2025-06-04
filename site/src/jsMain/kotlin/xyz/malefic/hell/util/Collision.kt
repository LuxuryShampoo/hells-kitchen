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
    val newX = position.x.coerceIn(0, 800 - 50)
    val newY = position.y.coerceIn(0, 500 - 70)

    for (obj in collisionObjects) {
        if (newX < obj.x + obj.width &&
            newX + 50 > obj.x &&
            newY < obj.y + obj.height &&
            newY + 70 > obj.y
        ) {
            return true
        }
    }

    return newX != position.x || newY != position.y
}
