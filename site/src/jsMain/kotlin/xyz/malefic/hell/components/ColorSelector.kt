package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun ColorSelector(
    label: String,
    selectedColor: String,
    colorOptions: List<String>,
    onColorSelected: (String) -> Unit,
) {
    Div(
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .margin(bottom = 20.px)
            .width(300.px)
            .toAttrs(),
    ) {
        Label(
            forId = null,
            attrs =
                Modifier
                    .color(CssColor.white)
                    .margin(bottom = 8.px)
                    .toAttrs(),
        ) {
            Text(label)
        }

        Div(
            Modifier
                .display(DisplayStyle.Flex)
                .flexWrap(FlexWrap.Wrap)
                .gap(10.px)
                .toAttrs(),
        ) {
            colorOptions.forEach { color ->
                val colorForClick = color

                Div(
                    Modifier
                        .size(40.px)
                        .backgroundColor(CssColor(color))
                        .cursor(Cursor.Pointer)
                        .borderRadius(4.px)
                        .border(
                            width = if (color == selectedColor) 3.px else 1.px,
                            style = LineStyle.Solid,
                            color = if (color == selectedColor) CssColor.white else CssColor("#666"),
                        ).onClick { onColorSelected(colorForClick) }
                        .toAttrs(),
                )
            }
        }
    }
}
