package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun WiiButton(
    isClicked: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            WiiHomeStyles.wiiButton
                .toModifier()
                .then(
                    if (!isClicked) {
                        Modifier
                            .border(4.px, LineStyle.Solid, CssColor("#ffe066"))
                            .boxShadow(0.px, 0.px, 8.px, 0.px, CssColor("rgba(255, 224, 102, 0.5)"))
                    } else {
                        Modifier
                    },
                ).onClick { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(CssColor.white)
                .fontWeight(FontWeight.Bold)
                .fontSize(24.px)
                .toAttrs(),
        ) {
            Text("Mii")
        }
    }
}
