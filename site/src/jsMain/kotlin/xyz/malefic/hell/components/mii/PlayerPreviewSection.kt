package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.player.CharacterColors
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun PlayerPreviewSection(
    hasSecondPlayer: Boolean,
    activePlayer: Int,
    player1Colors: CharacterColors,
    player2Colors: CharacterColors,
    currentColors: CharacterColors,
    onAddPlayer2: () -> Unit,
) {
    Div(
        Modifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(20.px)
            .margin(bottom = 40.px)
            .toAttrs(),
    ) {
        CharacterPreview(currentColors)
        if (!hasSecondPlayer) {
            Button(
                attrs = {
                    onClick { onAddPlayer2() }
                    style {
                        padding(12.px, 24.px)
                        backgroundColor(CssColor("#4CAF50"))
                        color(CssColor.white)
                        property("border", "none")
                        borderRadius(4.px)
                        fontSize(16.px)
                        fontWeight(FontWeight.Bold)
                        cursor(Cursor.Pointer)
                    }
                },
            ) { Text("Add Player 2") }
        }
    }
}
