package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.name
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.pages.Point
import xyz.malefic.hell.styles.KitchenStyles

@Composable
fun Player(characterPosition: Point) {
    Div(
        Modifier
            .classNames(KitchenStyles.character.name)
            .left(characterPosition.x.px)
            .top(characterPosition.y.px)
            .toAttrs(),
    ) {
        Div(
            Modifier
                .classNames(KitchenStyles.characterFace.name)
                .toAttrs(),
        ) {
            Div(
                Modifier
                    .size(5.px, 5.px)
                    .backgroundColor(Colors.White)
                    .position(Position.Absolute)
                    .left(3.px)
                    .top(2.px)
                    .borderRadius(50.percent)
                    .toAttrs(),
            )
            Div(
                Modifier
                    .size(5.px, 5.px)
                    .backgroundColor(Colors.White)
                    .position(Position.Absolute)
                    .left(12.px)
                    .top(2.px)
                    .borderRadius(50.percent)
                    .toAttrs(),
            )
        }
    }
}
