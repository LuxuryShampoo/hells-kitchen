package xyz.malefic.hell.util

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.GeneralKind
import com.varabyte.kobweb.silk.style.toModifier

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
