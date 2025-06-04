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
    var characterColors by remember { mutableStateOf(loadCharacterColors()) }

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
                .margin(bottom = 40.px)
                .toAttrs(),
        ) {
            Text("Customize Your Character")
        }

        // Character Preview
        Div(
            Modifier
                .width(200.px)
                .height(300.px)
                .backgroundColor(CssColor("#333"))
                .borderRadius(10.px)
                .padding(20.px)
                .margin(bottom = 40.px)
                .display(DisplayStyle.Flex)
                .justifyContent(JustifyContent.Center)
                .alignItems(AlignItems.Center)
                .toAttrs(),
        ) {
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
                        .backgroundColor(CssColor(characterColors.head))
                        .toAttrs(),
                )

                // Body
                Div(
                    Modifier
                        .size(width = 80.px, height = 100.px)
                        .backgroundColor(CssColor(characterColors.body))
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
                                .backgroundColor(CssColor(characterColors.arms))
                                .borderRadius(5.px)
                                .toAttrs(),
                        )
                        // Right arm
                        Div(
                            Modifier
                                .size(width = 20.px, height = 70.px)
                                .backgroundColor(CssColor(characterColors.arms))
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
                            .backgroundColor(CssColor(characterColors.legs))
                            .borderRadius(5.px)
                            .toAttrs(),
                    )
                    // Right leg
                    Div(
                        Modifier
                            .size(width = 25.px, height = 80.px)
                            .backgroundColor(CssColor(characterColors.legs))
                            .borderRadius(5.px)
                            .toAttrs(),
                    )
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

        // Body Part Selectors
        colorSelector("Head Color", characterColors.head, colorOptions) { newColor ->
            characterColors = characterColors.copy(head = newColor)
            saveCharacterColors(characterColors)
        }

        colorSelector("Body Color", characterColors.body, colorOptions) { newColor ->
            characterColors = characterColors.copy(body = newColor)
            saveCharacterColors(characterColors)
        }

        colorSelector("Arms Color", characterColors.arms, colorOptions) { newColor ->
            characterColors = characterColors.copy(arms = newColor)
            saveCharacterColors(characterColors)
        }

        colorSelector("Legs Color", characterColors.legs, colorOptions) { newColor ->
            characterColors = characterColors.copy(legs = newColor)
            saveCharacterColors(characterColors)
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

private fun loadCharacterColors(): CharacterColors {
    val headColor = localStorage.getItem("character_head") ?: "#FFD590"
    val bodyColor = localStorage.getItem("character_body") ?: "#FF0000"
    val armsColor = localStorage.getItem("character_arms") ?: "#FFD590"
    val legsColor = localStorage.getItem("character_legs") ?: "#0000FF"

    return CharacterColors(headColor, bodyColor, armsColor, legsColor)
}

private fun saveCharacterColors(colors: CharacterColors) {
    localStorage.setItem("character_head", colors.head)
    localStorage.setItem("character_body", colors.body)
    localStorage.setItem("character_arms", colors.arms)
    localStorage.setItem("character_legs", colors.legs)
}
