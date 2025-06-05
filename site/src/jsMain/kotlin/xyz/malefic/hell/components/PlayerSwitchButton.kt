package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.fontWeight
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun PlayerSwitchButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    Button(
        attrs = {
            onClick { onClick() }
            style {
                padding(8.px, 16.px)
                backgroundColor(if (isActive) CssColor("#4CAF50") else CssColor("#555"))
                color(CssColor.white)
                property("border", "none")
                borderRadius(4.px)
                fontSize(14.px)
                fontWeight(if (isActive) FontWeight.Bold else FontWeight.Normal)
                cursor(Cursor.Pointer)
            }
        },
    ) {
        Text(text)
    }
}
