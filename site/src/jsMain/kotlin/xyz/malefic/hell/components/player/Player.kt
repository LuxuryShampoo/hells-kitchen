package xyz.malefic.hell.components.player

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun Player(
    position: PlayerPosition,
    playerId: Int,
) = Box(
    Modifier
        .position(Position.Absolute)
        .left(position.x.px)
        .top(position.y.px)
        .size(width = 50.px, height = 70.px)
        .zIndex(10),
) {
    Div(
        Modifier
            .fillMaxSize()
            .position(Position.Relative)
            .toAttrs(),
    ) {
        val colors = loadCharacterColors(playerId)

        Head(colors)

        Torso(colors)

        Arms(position, colors)

        Legs(position, colors)
    }
}

@Composable
private fun Head(colors: CharacterColors) =
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

@Composable
private fun Torso(colors: CharacterColors) =
    Div(
        Modifier
            .position(Position.Absolute)
            .size(width = 30.px, height = 25.px)
            .backgroundColor(CssColor(colors.body))
            .left(10.px)
            .top(30.px)
            .toAttrs(),
    )

@Composable
private fun Arms(
    position: PlayerPosition,
    colors: CharacterColors,
) = if (position.direction in setOf(Direction.LEFT, Direction.RIGHT)) {
    Div(
        Modifier
            .position(Position.Absolute)
            .size(width = 10.px, height = 20.px)
            .backgroundColor(CssColor(colors.arms))
            .left(if (position.isMoving && position.direction == Direction.LEFT) 5.px else 0.px)
            .top(30.px)
            .toAttrs(),
    )

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

@Composable
private fun Legs(
    position: PlayerPosition,
    colors: CharacterColors,
) = if (position.isMoving) {
    Div(
        Modifier
            .position(Position.Absolute)
            .size(width = 10.px, height = 15.px)
            .backgroundColor(CssColor(colors.legs))
            .left(10.px)
            .top(55.px)
            .toAttrs(),
    )

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
