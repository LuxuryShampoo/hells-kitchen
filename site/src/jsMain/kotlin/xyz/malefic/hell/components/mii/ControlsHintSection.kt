package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun ControlsHintSection() {
    Div(
        Modifier
            .margin(top = 20.px)
            .padding(10.px)
            .border(1.px, LineStyle.Solid, CssColor("#444"))
            .borderRadius(5.px)
            .backgroundColor(CssColor("#2a2a2a"))
            .color(CssColor("#FFF"))
            .toAttrs(),
    ) {
        Text("Player 1 Controls: Arrow Keys")
        Div { Text(" ") }
        Text("Player 2 Controls: WASD")
    }
}
