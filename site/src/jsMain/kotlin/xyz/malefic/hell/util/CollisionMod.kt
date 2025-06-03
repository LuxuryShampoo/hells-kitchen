package xyz.malefic.hell.util

import com.varabyte.kobweb.compose.ui.Modifier

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
