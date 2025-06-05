package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.window
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.ColorSelector
import xyz.malefic.hell.components.player.CharacterColors
import xyz.malefic.hell.components.player.colorsMatch
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun ColorSelectorsSection(
    colorOptions: List<String>,
    currentColors: CharacterColors,
    otherPlayerColors: CharacterColors,
    hasSecondPlayer: Boolean,
    activePlayer: Int,
    onUpdateColors: (CharacterColors, Int) -> Unit,
) {
    H2(
        Modifier
            .color(CssColor.white)
            .margin(bottom = 20.px)
            .toAttrs(),
    ) { Text("Select Colors") }

    fun tryUpdate(newColors: CharacterColors) {
        if (!hasSecondPlayer ||
            !colorsMatch(
                newColors.head,
                otherPlayerColors.head,
                newColors.body,
                otherPlayerColors.body,
                newColors.arms,
                otherPlayerColors.arms,
                newColors.legs,
                otherPlayerColors.legs,
            )
        ) {
            onUpdateColors(newColors, activePlayer)
        } else {
            window.alert("Players cannot have identical color schemes. Please choose a different color.")
        }
    }

    ColorSelector("Head Color", currentColors.head, colorOptions) { newColor ->
        tryUpdate(currentColors.copy(head = newColor))
    }
    ColorSelector("Body Color", currentColors.body, colorOptions) { newColor ->
        tryUpdate(currentColors.copy(body = newColor))
    }
    ColorSelector("Arms Color", currentColors.arms, colorOptions) { newColor ->
        tryUpdate(currentColors.copy(arms = newColor))
    }
    ColorSelector("Legs Color", currentColors.legs, colorOptions) { newColor ->
        tryUpdate(currentColors.copy(legs = newColor))
    }
}
