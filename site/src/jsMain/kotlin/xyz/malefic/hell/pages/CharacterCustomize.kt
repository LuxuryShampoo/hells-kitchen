package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color as CssColor

data class CharacterColors(
    val head: String = "#FFD590",
    val body: String = "#FF0000",
    val arms: String = "#FFD590",
    val legs: String = "#0000FF",
)

@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun LaunchedEffect(
    key: Any,
    block: suspend () -> Unit,
) {
    DisposableEffect(key) {
        val job =
            GlobalScope.launch {
                block()
            }
        onDispose { job.cancel() }
    }
}

@Page("/character-customize")
@Composable
fun CharacterCustomizePage() {
    // Load player characters from localStorage
    var player1Colors by remember { mutableStateOf(loadCharacterColors(1)) }
    var player2Colors by remember { mutableStateOf(loadCharacterColors(2)) }
    var hasSecondPlayer by remember { mutableStateOf(localStorage.getItem("has_second_player") == "true") }
    var activePlayer by remember { mutableStateOf(1) }

    // Set miiClicked to true in localStorage when the page loads
    LaunchedEffect(Unit) {
        localStorage.setItem("mii_clicked", "true")
    }

    val colorOptions =
        listOf(
            "#FF0000", // Red
            "#00FF00", // Green
            "#0000FF", // Blue
            "#FFFF00", // Yellow
            "#FF00FF", // Magenta
            "#00FFFF", // Cyan
            "#FFD590", // Skin tone
            "#000000", // Black
        )

    Div(
        Modifier
            .fillMaxSize()
            .backgroundColor(CssColor("#1a1a1a"))
            .padding(20.px)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .overflow(Overflow.Auto)
            .toAttrs(),
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

        // Player selection buttons (if there are two players)
        if (hasSecondPlayer) {
            Div(
                Modifier
                    .display(DisplayStyle.Flex)
                    .gap(10.px)
                    .margin(bottom = 20.px)
                    .toAttrs(),
            ) {
                playerSwitchButton("Player 1", activePlayer == 1) { activePlayer = 1 }
                playerSwitchButton("Player 2", activePlayer == 2) { activePlayer = 2 }
            }
        }

        // Character Preview Section
        Div(
            Modifier
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(20.px)
                .margin(bottom = 40.px)
                .toAttrs(),
        ) {
            // Character Preview
            Div(
                Modifier
                    .width(200.px)
                    .height(300.px)
                    .backgroundColor(CssColor("#333"))
                    .borderRadius(10.px)
                    .padding(20.px)
                    .display(DisplayStyle.Flex)
                    .justifyContent(JustifyContent.Center)
                    .alignItems(AlignItems.Center)
                    .toAttrs(),
            ) {
                // Current active character
                val currentColors = if (activePlayer == 1) player1Colors else player2Colors

                // Simple pixel art character
                Div(
                    Modifier
                        .display(DisplayStyle.Flex)
                        .flexDirection(FlexDirection.Column)
                        .alignItems(AlignItems.Center)
                        .toAttrs(),
                ) {
                    // Head
                    Div(
                        Modifier
                            .size(60.px)
                            .borderRadius(50.percent)
                            .backgroundColor(CssColor(currentColors.head))
                            .toAttrs(),
                    )

                    // Body
                    Div(
                        Modifier
                            .size(width = 80.px, height = 100.px)
                            .backgroundColor(CssColor(currentColors.body))
                            .borderRadius(5.px)
                            .margin(top = 5.px)
                            .toAttrs(),
                    ) {
                        // Arms
                        Div(
                            Modifier
                                .display(DisplayStyle.Flex)
                                .justifyContent(JustifyContent.SpaceBetween)
                                .width(120.px)
                                .margin(leftRight = (-20).px, top = 15.px)
                                .toAttrs(),
                        ) {
                            // Left arm
                            Div(
                                Modifier
                                    .size(width = 20.px, height = 70.px)
                                    .backgroundColor(CssColor(currentColors.arms))
                                    .borderRadius(5.px)
                                    .toAttrs(),
                            )
                            // Right arm
                            Div(
                                Modifier
                                    .size(width = 20.px, height = 70.px)
                                    .backgroundColor(CssColor(currentColors.arms))
                                    .borderRadius(5.px)
                                    .toAttrs(),
                            )
                        }
                    }

                    // Legs
                    Div(
                        Modifier
                            .display(DisplayStyle.Flex)
                            .justifyContent(JustifyContent.SpaceBetween)
                            .width(60.px)
                            .toAttrs(),
                    ) {
                        // Left leg
                        Div(
                            Modifier
                                .size(width = 25.px, height = 80.px)
                                .backgroundColor(CssColor(currentColors.legs))
                                .borderRadius(5.px)
                                .toAttrs(),
                        )
                        // Right leg
                        Div(
                            Modifier
                                .size(width = 25.px, height = 80.px)
                                .backgroundColor(CssColor(currentColors.legs))
                                .borderRadius(5.px)
                                .toAttrs(),
                        )
                    }
                }
            }

            // Add Player Button (show only if there's no second player yet)
            if (!hasSecondPlayer) {
                Button(
                    attrs = {
                        onClick {
                            hasSecondPlayer = true
                            localStorage.setItem("has_second_player", "true")
                            activePlayer = 2
                            // Ensure player 2 has different colors
                            player2Colors = generateDifferentColors(player1Colors)
                            saveCharacterColors(player2Colors, 2)
                        }
                        style {
                            property("padding", "12px 24px")
                            property("background-color", "#4CAF50")
                            property("color", "white")
                            property("border", "none")
                            property("border-radius", "4px")
                            property("font-size", "16px")
                            property("font-weight", "bold")
                            property("cursor", "pointer")
                        }
                    },
                ) {
                    Text("Add Player 2")
                }
            }
        }

        // Color Selection Area
        H2(
            Modifier
                .color(CssColor.white)
                .margin(bottom = 20.px)
                .toAttrs(),
        ) {
            Text("Select Colors")
        }

        val currentColors = if (activePlayer == 1) player1Colors else player2Colors
        val otherPlayerColors = if (activePlayer == 1) player2Colors else player1Colors

        // Body Part Selectors
        colorSelector("Head Color", currentColors.head, colorOptions) { newColor ->
            if (!hasSecondPlayer ||
                !colorsMatch(
                    newColor,
                    otherPlayerColors.head,
                    currentColors.body,
                    otherPlayerColors.body,
                    currentColors.arms,
                    otherPlayerColors.arms,
                    currentColors.legs,
                    otherPlayerColors.legs,
                )
            ) {
                if (activePlayer == 1) {
                    player1Colors = player1Colors.copy(head = newColor)
                    saveCharacterColors(player1Colors, 1)
                } else {
                    player2Colors = player2Colors.copy(head = newColor)
                    saveCharacterColors(player2Colors, 2)
                }
            } else {
                window.alert("Players cannot have identical color schemes. Please choose a different color.")
            }
        }

        colorSelector("Body Color", currentColors.body, colorOptions) { newColor ->
            if (!hasSecondPlayer ||
                !colorsMatch(
                    currentColors.head,
                    otherPlayerColors.head,
                    newColor,
                    otherPlayerColors.body,
                    currentColors.arms,
                    otherPlayerColors.arms,
                    currentColors.legs,
                    otherPlayerColors.legs,
                )
            ) {
                if (activePlayer == 1) {
                    player1Colors = player1Colors.copy(body = newColor)
                    saveCharacterColors(player1Colors, 1)
                } else {
                    player2Colors = player2Colors.copy(body = newColor)
                    saveCharacterColors(player2Colors, 2)
                }
            } else {
                window.alert("Players cannot have identical color schemes. Please choose a different color.")
            }
        }

        colorSelector("Arms Color", currentColors.arms, colorOptions) { newColor ->
            if (!hasSecondPlayer ||
                !colorsMatch(
                    currentColors.head,
                    otherPlayerColors.head,
                    currentColors.body,
                    otherPlayerColors.body,
                    newColor,
                    otherPlayerColors.arms,
                    currentColors.legs,
                    otherPlayerColors.legs,
                )
            ) {
                if (activePlayer == 1) {
                    player1Colors = player1Colors.copy(arms = newColor)
                    saveCharacterColors(player1Colors, 1)
                } else {
                    player2Colors = player2Colors.copy(arms = newColor)
                    saveCharacterColors(player2Colors, 2)
                }
            } else {
                window.alert("Players cannot have identical color schemes. Please choose a different color.")
            }
        }

        colorSelector("Legs Color", currentColors.legs, colorOptions) { newColor ->
            if (!hasSecondPlayer ||
                !colorsMatch(
                    currentColors.head,
                    otherPlayerColors.head,
                    currentColors.body,
                    otherPlayerColors.body,
                    currentColors.arms,
                    otherPlayerColors.arms,
                    newColor,
                    otherPlayerColors.legs,
                )
            ) {
                if (activePlayer == 1) {
                    player1Colors = player1Colors.copy(legs = newColor)
                    saveCharacterColors(player1Colors, 1)
                } else {
                    player2Colors = player2Colors.copy(legs = newColor)
                    saveCharacterColors(player2Colors, 2)
                }
            } else {
                window.alert("Players cannot have identical color schemes. Please choose a different color.")
            }
        }

        // Control instructions
        if (hasSecondPlayer) {
            Div(
                Modifier
                    .margin(top = 20.px)
                    .padding(10.px)
                    .border(1.px, LineStyle.Solid, CssColor("#444"))
                    .borderRadius(5.px)
                    .backgroundColor(CssColor("#2a2a2a"))
                    .color(CssColor("#FFF"))
                    .toAttrs(),
            ) {
                Text("Player 1 Controls: Arrow Keys")
                Div { Text(" ") }
                Text("Player 2 Controls: WASD")
            }
        }

        // Done Button
        Button(
            attrs = {
                onClick { window.location.href = "/" }
                style {
                    property("padding", "12px 24px")
                    property("background-color", "#4CAF50")
                    property("color", "white")
                    property("border", "none")
                    property("border-radius", "4px")
                    property("font-size", "16px")
                    property("font-weight", "bold")
                    property("cursor", "pointer")
                    property("margin-top", "40px")
                }
            },
        ) {
            Text("Done")
        }

        // Remove Player 2 Button (only show when editing Player 2)
        if (hasSecondPlayer && activePlayer == 2) {
            Button(
                attrs = {
                    onClick {
                        hasSecondPlayer = false
                        localStorage.setItem("has_second_player", "false")
                        activePlayer = 1
                    }
                    style {
                        property("padding", "12px 24px")
                        property("background-color", "#e74c3c")
                        property("color", "white")
                        property("border", "none")
                        property("border-radius", "4px")
                        property("font-size", "16px")
                        property("font-weight", "bold")
                        property("cursor", "pointer")
                        property("margin-top", "20px")
                    }
                },
            ) {
                Text("Remove Player 2")
            }
        }
    }
}

@Composable
private fun playerSwitchButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    Button(
        attrs = {
            onClick { onClick() }
            style {
                property("padding", "8px 16px")
                property("background-color", if (isActive) "#4CAF50" else "#555")
                property("color", "white")
                property("border", "none")
                property("border-radius", "4px")
                property("font-size", "14px")
                property("font-weight", if (isActive) "bold" else "normal")
                property("cursor", "pointer")
            }
        },
    ) {
        Text(text)
    }
}

@Composable
private fun colorSelector(
    label: String,
    selectedColor: String,
    colorOptions: List<String>,
    onColorSelected: (String) -> Unit,
) {
    Div(
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .margin(bottom = 20.px)
            .width(300.px)
            .toAttrs(),
    ) {
        Label(
            forId = null,
            attrs =
                Modifier
                    .color(CssColor.white)
                    .margin(bottom = 8.px)
                    .toAttrs(),
        ) {
            Text(label)
        }

        Div(
            Modifier
                .display(DisplayStyle.Flex)
                .flexWrap(FlexWrap.Wrap)
                .gap(10.px)
                .toAttrs(),
        ) {
            colorOptions.forEach { color ->
                // Create a final color value to capture for the onClick
                val colorForClick = color

                Div(
                    Modifier
                        .size(40.px)
                        .backgroundColor(CssColor(color))
                        .cursor(Cursor.Pointer)
                        .borderRadius(4.px)
                        .border(
                            width = if (color == selectedColor) 3.px else 1.px,
                            style = LineStyle.Solid,
                            color = if (color == selectedColor) CssColor.white else CssColor("#666"),
                        ).onClick { onColorSelected(colorForClick) }
                        .toAttrs(),
                ) {}
            }
        }
    }
}

private fun loadCharacterColors(playerNumber: Int = 1): CharacterColors {
    val prefix = if (playerNumber == 1) "character_" else "character2_"

    val headColor = localStorage.getItem("${prefix}head") ?: if (playerNumber == 1) "#FFD590" else "#FFD590"
    val bodyColor = localStorage.getItem("${prefix}body") ?: if (playerNumber == 1) "#FF0000" else "#00FF00"
    val armsColor = localStorage.getItem("${prefix}arms") ?: if (playerNumber == 1) "#FFD590" else "#FFD590"
    val legsColor = localStorage.getItem("${prefix}legs") ?: if (playerNumber == 1) "#0000FF" else "#000000"

    return CharacterColors(headColor, bodyColor, armsColor, legsColor)
}

private fun saveCharacterColors(
    colors: CharacterColors,
    playerNumber: Int = 1,
) {
    val prefix = if (playerNumber == 1) "character_" else "character2_"

    localStorage.setItem("${prefix}head", colors.head)
    localStorage.setItem("${prefix}body", colors.body)
    localStorage.setItem("${prefix}arms", colors.arms)
    localStorage.setItem("${prefix}legs", colors.legs)
}

private fun colorsMatch(
    head1: String,
    head2: String,
    body1: String,
    body2: String,
    arms1: String,
    arms2: String,
    legs1: String,
    legs2: String,
): Boolean = head1 == head2 && body1 == body2 && arms1 == arms2 && legs1 == legs2

private fun generateDifferentColors(player1Colors: CharacterColors): CharacterColors {
    // Create a different color scheme for player 2
    val colorOptions = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#FFD590", "#000000")

    // Use different colors than player 1
    val bodyColor = colorOptions.firstOrNull { it != player1Colors.body } ?: "#00FF00"
    val legsColor = colorOptions.firstOrNull { it != player1Colors.legs } ?: "#000000"

    // Return a character with same skin tone but different clothes
    return CharacterColors(
        head = player1Colors.head,
        body = bodyColor,
        arms = player1Colors.arms,
        legs = legsColor,
    )
}
