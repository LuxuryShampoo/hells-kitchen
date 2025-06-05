package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.components.PlayerSwitchButton

@Composable
fun PlayerSwitchSection(
    activePlayer: Int,
    onSwitch: (Int) -> Unit,
) {
    Div(
        Modifier
            .display(DisplayStyle.Flex)
            .gap(10.px)
            .margin(bottom = 20.px)
            .toAttrs(),
    ) {
        PlayerSwitchButton("Player 1", activePlayer == 1) { onSwitch(1) }
        PlayerSwitchButton("Player 2", activePlayer == 2) { onSwitch(2) }
    }
}
