package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun TitleSection(
    hasSecondPlayer: Boolean,
    activePlayer: Int,
) {
    H1(
        Modifier
            .color(CssColor.white)
            .fontWeight(FontWeight.Bold)
            .margin(bottom = 20.px)
            .toAttrs(),
    ) {
        Text(if (hasSecondPlayer) "Player $activePlayer Customization" else "Customize Your Character")
    }
}
