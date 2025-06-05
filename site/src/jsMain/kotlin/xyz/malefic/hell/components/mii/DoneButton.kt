package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.fontWeight
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun DoneButton(onClick: () -> Unit) {
    Button(
        attrs = {
            onClick { onClick() }
            style {
                padding(12.px, 24.px)
                backgroundColor(CssColor("#4CAF50"))
                color(CssColor.white)
                property("border", "none")
                borderRadius(4.px)
                fontSize(16.px)
                fontWeight(FontWeight.Bold)
                cursor(Cursor.Pointer)
                marginTop(40.px)
            }
        },
    ) { Text("Done") }
}
