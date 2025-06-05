package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.ColorSelector
import xyz.malefic.hell.components.PlayerSwitchButton
import xyz.malefic.hell.components.player.CharacterColors
import xyz.malefic.hell.components.player.colorsMatch
import xyz.malefic.hell.components.player.generateDifferentColors
import xyz.malefic.hell.components.player.loadCharacterColors
import xyz.malefic.hell.components.player.saveCharacterColors
import org.jetbrains.compose.web.css.Color as CssColor

@Page("/character-customize")
@Composable
fun CharacterCustomizePage(ctx: PageContext) {
    var player1Colors by remember { mutableStateOf(loadCharacterColors(1)) }
    var player2Colors by remember { mutableStateOf(loadCharacterColors(2)) }
    var hasSecondPlayer by remember { mutableStateOf(localStorage.getItem("has_second_player") == "true") }

    var activePlayer by remember {
        val params = window.location.search.let { if (it.startsWith("?")) it.substring(1) else it }
        val playerParam =
            params
                .split("&")
                .find { it.startsWith("player=") }
                ?.split("=")
                ?.getOrNull(1)
        mutableStateOf(playerParam?.toIntOrNull() ?: 1)
    }

    LaunchedEffect(Unit) {
        localStorage.setItem("mii_clicked", "true")
        if (activePlayer == 2 && !hasSecondPlayer) {
            hasSecondPlayer = true
            localStorage.setItem("has_second_player", "true")
            if (player2Colors ==
                CharacterColors(
                    head = "#FFD590",
                    body = "#FF0000",
                    arms = "#FFD590",
                    legs = "#0000FF",
                )
            ) {
                player2Colors = generateDifferentColors(player1Colors)
                saveCharacterColors(player2Colors, 2)
            }
        }
    }

    val colorOptions =
        listOf(
            "#FF0000",
            "#00FF00",
            "#0000FF",
            "#FFFF00",
            "#FF00FF",
            "#00FFFF",
            "#FFD590",
            "#000000",
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

        if (hasSecondPlayer) {
            Div(
                Modifier
                    .display(DisplayStyle.Flex)
                    .gap(10.px)
                    .margin(bottom = 20.px)
                    .toAttrs(),
            ) {
                PlayerSwitchButton("Player 1", activePlayer == 1) { activePlayer = 1 }
                PlayerSwitchButton("Player 2", activePlayer == 2) { activePlayer = 2 }
            }
        }

        Div(
            Modifier
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(20.px)
                .margin(bottom = 40.px)
                .toAttrs(),
        ) {
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
                val currentColors = if (activePlayer == 1) player1Colors else player2Colors

                Div(
                    Modifier
                        .display(DisplayStyle.Flex)
                        .flexDirection(FlexDirection.Column)
                        .alignItems(AlignItems.Center)
                        .toAttrs(),
                ) {
                    Div(
                        Modifier
                            .size(60.px)
                            .borderRadius(50.percent)
                            .backgroundColor(CssColor(currentColors.head))
                            .toAttrs(),
                    )

                    Div(
                        Modifier
                            .size(width = 80.px, height = 100.px)
                            .backgroundColor(CssColor(currentColors.body))
                            .borderRadius(5.px)
                            .margin(top = 5.px)
                            .toAttrs(),
                    ) {
                        Div(
                            Modifier
                                .display(DisplayStyle.Flex)
                                .justifyContent(JustifyContent.SpaceBetween)
                                .width(120.px)
                                .margin(leftRight = (-20).px, top = 15.px)
                                .toAttrs(),
                        ) {
                            Div(
                                Modifier
                                    .size(width = 20.px, height = 70.px)
                                    .backgroundColor(CssColor(currentColors.arms))
                                    .borderRadius(5.px)
                                    .toAttrs(),
                            )
                            Div(
                                Modifier
                                    .size(width = 20.px, height = 70.px)
                                    .backgroundColor(CssColor(currentColors.arms))
                                    .borderRadius(5.px)
                                    .toAttrs(),
                            )
                        }
                    }

                    Div(
                        Modifier
                            .display(DisplayStyle.Flex)
                            .justifyContent(JustifyContent.SpaceBetween)
                            .width(60.px)
                            .toAttrs(),
                    ) {
                        Div(
                            Modifier
                                .size(width = 25.px, height = 80.px)
                                .backgroundColor(CssColor(currentColors.legs))
                                .borderRadius(5.px)
                                .toAttrs(),
                        )
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

            if (!hasSecondPlayer) {
                Button(
                    attrs = {
                        onClick {
                            hasSecondPlayer = true
                            localStorage.setItem("has_second_player", "true")
                            activePlayer = 2
                            player2Colors = generateDifferentColors(player1Colors)
                            saveCharacterColors(player2Colors, 2)
                        }
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
                ) {
                    Text("Add Player 2")
                }
            }
        }

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

        ColorSelector("Head Color", currentColors.head, colorOptions) { newColor ->
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

        ColorSelector("Body Color", currentColors.body, colorOptions) { newColor ->
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

        ColorSelector("Arms Color", currentColors.arms, colorOptions) { newColor ->
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

        ColorSelector("Legs Color", currentColors.legs, colorOptions) { newColor ->
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

        Button(
            attrs = {
                onClick { ctx.router.navigateTo("/") }
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
        ) {
            Text("Done")
        }

        if (hasSecondPlayer && activePlayer == 2) {
            Button(
                attrs = {
                    onClick {
                        hasSecondPlayer = false
                        localStorage.setItem("has_second_player", "false")
                        activePlayer = 1
                    }
                    style {
                        padding(12.px, 24.px)
                        backgroundColor(CssColor("#e74c3c"))
                        color(CssColor.white)
                        property("border", "none")
                        borderRadius(4.px)
                        fontSize(16.px)
                        fontWeight(FontWeight.Bold)
                        cursor(Cursor.Pointer)
                        marginTop(20.px)
                    }
                },
            ) {
                Text("Remove Player 2")
            }
        }
    }
}
