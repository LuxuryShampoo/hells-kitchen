package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun WiiChannel(
    name: String,
    color: String,
) {
    Box(
        modifier =
            WiiHomeStyles.channelButton
                .toModifier()
                .then(Modifier.backgroundColor(mapColorNameToColor(color))),
        contentAlignment = Alignment.Center,
    ) {
        Div(
            Modifier
                .fillMaxSize()
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
                .justifyContent(JustifyContent.Center)
                .alignItems(AlignItems.Center)
                .toAttrs(),
        ) {
            Span(
                Modifier
                    .color(CssColor.white)
                    .textAlign(TextAlign.Center)
                    .fontWeight(FontWeight.Bold)
                    .fontSize(16.px)
                    .toAttrs(),
            ) {
                Text(name)
            }
        }
    }
}

private fun mapColorNameToColor(colorName: String): Color =
    when (colorName.lowercase()) {
        "blue" -> Color.rgb(0, 102, 204)
        "green" -> Color.rgb(34, 139, 34)
        "yellow" -> Color.rgb(255, 215, 0)
        "red" -> Color.rgb(215, 30, 50)
        "orange" -> Color.rgb(255, 140, 0)
        "darkgray" -> Color.rgb(64, 64, 64)
        else -> Color.rgb(240, 240, 240)
    }
