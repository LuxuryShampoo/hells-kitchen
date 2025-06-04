@file:Suppress("ktlint:standard:filename")

package xyz.malefic.hell.components.player

import kotlinx.browser.localStorage

fun loadCharacterColors(playerId: Int): CharacterColors {
    val defaultHeadColor = "#FFD590"
    val p1DefaultBodyColor = "#FF0000"
    val p2DefaultBodyColor = "#0000FF"
    val defaultArmsColor = "#FFD590"
    val defaultLegsColor = "#0000FF"

    val headColor = localStorage.getItem("character_head_p$playerId") ?: defaultHeadColor
    val armsColor = localStorage.getItem("character_arms_p$playerId") ?: defaultArmsColor
    val legsColor = localStorage.getItem("character_legs_p$playerId") ?: defaultLegsColor

    val bodyColor =
        localStorage.getItem("character_body_p$playerId")
            ?: when (playerId) {
                1 -> p1DefaultBodyColor
                else -> p2DefaultBodyColor
            }

    return CharacterColors(headColor, bodyColor, armsColor, legsColor)
}

data class CharacterColors(
    val head: String = "#FFD590",
    val body: String = "#FF0000",
    val arms: String = "#FFD590",
    val legs: String = "#0000FF",
)
