package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles

@Composable
fun FooterBar(
    time: String,
    date: String,
) {
    Box(
        modifier = WiiHomeStyles.footer.toModifier(),
        contentAlignment = Alignment.Center,
    ) {
        Div(
            attrs =
                Modifier
                    .fillMaxSize()
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
                    .justifyContent(JustifyContent.Center)
                    .alignItems(AlignItems.Center)
                    .toAttrs(),
        ) {
            P(
                attrs = WiiHomeStyles.clock.toModifier().toAttrs(),
            ) {
                Text(time)
            }
            P(
                attrs = WiiHomeStyles.date.toModifier().toAttrs(),
            ) {
                Text(date)
            }
        }
    }
}
