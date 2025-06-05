package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import kotlinx.browser.localStorage
import xyz.malefic.hell.components.mii.ColorSelectorsSection
import xyz.malefic.hell.components.mii.ControlsHintSection
import xyz.malefic.hell.components.mii.DoneButton
import xyz.malefic.hell.components.mii.PlayerPreviewSection
import xyz.malefic.hell.components.mii.PlayerSwitchSection
import xyz.malefic.hell.components.mii.RemovePlayer2Button
import xyz.malefic.hell.components.mii.TitleSection
import xyz.malefic.hell.components.player.CharacterColors
import xyz.malefic.hell.components.player.generateDifferentColors
import xyz.malefic.hell.components.player.loadCharacterColors
import xyz.malefic.hell.components.player.saveCharacterColors

@Composable
@Page("/character-customize")
@Layout(".components.layouts.MiiCustomizeLayout")
fun CustomizeMii(ctx: PageContext) {
    var player1Colors by remember { mutableStateOf(loadCharacterColors(1)) }
    var player2Colors by remember { mutableStateOf(loadCharacterColors(2)) }
    var hasSecondPlayer by remember { mutableStateOf(localStorage.getItem("has_second_player") == "true") }
    var activePlayer by remember {
        val playerParam = ctx.route.params["player"]
        mutableStateOf(playerParam?.toIntOrNull() ?: 1)
    }

    LaunchedEffect(Unit) {
        localStorage.setItem("mii_clicked", "true")
        if (activePlayer == 2 && !hasSecondPlayer) {
            hasSecondPlayer = true
            localStorage.setItem("has_second_player", "true")
            if (player2Colors == CharacterColors("#FFD590", "#FF0000", "#FFD590", "#0000FF")) {
                player2Colors = generateDifferentColors(player1Colors)
                saveCharacterColors(player2Colors, 2)
            }
        }
    }

    val colorOptions = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#FFD590", "#000000")
    val currentColors = if (activePlayer == 1) player1Colors else player2Colors
    val otherPlayerColors = if (activePlayer == 1) player2Colors else player1Colors

    TitleSection(hasSecondPlayer, activePlayer)
    if (hasSecondPlayer) PlayerSwitchSection(activePlayer) { activePlayer = it }
    PlayerPreviewSection(
        hasSecondPlayer,
        activePlayer,
        player1Colors,
        player2Colors,
        currentColors,
    ) {
        hasSecondPlayer = true
        localStorage.setItem("has_second_player", "true")
        activePlayer = 2
        player2Colors = generateDifferentColors(player1Colors)
        saveCharacterColors(player2Colors, 2)
    }
    ColorSelectorsSection(
        colorOptions,
        currentColors,
        otherPlayerColors,
        hasSecondPlayer,
        activePlayer,
    ) { newColors, player ->
        if (player == 1) {
            player1Colors = newColors
            saveCharacterColors(player1Colors, 1)
        } else {
            player2Colors = newColors
            saveCharacterColors(player2Colors, 2)
        }
    }
    if (hasSecondPlayer) ControlsHintSection()
    DoneButton { ctx.router.navigateTo("/") }
    if (hasSecondPlayer && activePlayer == 2) {
        RemovePlayer2Button {
            hasSecondPlayer = false
            localStorage.setItem("has_second_player", "false")
            activePlayer = 1
        }
    }
}
