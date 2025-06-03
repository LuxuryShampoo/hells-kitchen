package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun WiiButton() {
    Box(
        modifier = WiiHomeStyles.wiiButton.toModifier(),
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(CssColor.white)
                .fontWeight(FontWeight.Bold)
                .fontSize(24.px)
                .toAttrs(),
        ) {
            Text("Wii")
        }
    }
}
