package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun WiiChannel(
    text: String,
    color: String,
    onClick: () -> Unit = {}
) {
    val isDisabled = color == "darkgray"
    
    Box(
        modifier = Modifier
            .width(80.px)
            .height(80.px)
            .margin(8.px)
            .borderRadius(10.px)
            .background(CssColor(color))
            .opacity(if (isDisabled) 0.7 else 1.0)
            .cursor(if (isDisabled) Cursor.NotAllowed else Cursor.Pointer)
            .onClick { if (!isDisabled) onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(CssColor.white)
                .fontWeight(FontWeight.Bold)
                .fontSize(16.px)
                .opacity(if (isDisabled) 0.6 else 1.0)
                .toAttrs(),
        ) {
            Text(text)
        }
    }
}
