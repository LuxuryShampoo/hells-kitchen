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
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun WiiChannel(
    text: String,
    color: String,
    onClick: () -> Unit = {},
) {
    val isDisabled = color == "darkgray"
    val actualColor = if (isDisabled) "#444444" else color // Darker grey

    Box(
        modifier =
            Modifier
                .width(140.px)
                .height(100.px)
                .margin(10.px)
                .borderRadius(12.px)
                .background(CssColor(actualColor))
                .cursor(if (isDisabled) Cursor.NotAllowed else Cursor.Pointer)
                .onClick { if (!isDisabled) onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(CssColor.white)
                .fontWeight(FontWeight.Bold)
                .fontSize(18.px)
                .toAttrs(),
        ) {
            Text(text)
        }
    }
}
