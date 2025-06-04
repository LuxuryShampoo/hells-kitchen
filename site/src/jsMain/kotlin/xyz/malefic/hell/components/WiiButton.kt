package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles

@Composable
fun WiiButton(
    modifier: Modifier = Modifier,
    isClicked: Boolean = false,
    onClick: () -> Unit = {},
) {
    val textColor = Color.rgb(70, 70, 70)

    Box(
        modifier = WiiHomeStyles.wiiButton.toModifier()
            .then(modifier)
            .onClick {
                onClick()
                localStorage.setItem("mii_clicked", "true")
                window.location.href = "/character-customize"
            },
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(textColor)
                .fontWeight(FontWeight.Bold)
                .fontSize(24.px)
                .toAttrs(),
        ) {
            Text("Mii")
        }
    }
}
