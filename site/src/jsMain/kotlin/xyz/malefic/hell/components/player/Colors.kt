@file:Suppress("ktlint:standard:filename")

package xyz.malefic.hell.components.player

import kotlinx.browser.localStorage

fun loadCharacterColors(playerNumber: Int = 1): CharacterColors {
    val suffix = "_p$playerNumber"

    val defaultHead = "#FFD590"
    val defaultP1Body = "#FF0000"
    val defaultP2Body = "#0000FF"
    val defaultArms = "#FFD590"
    val defaultLegs = "#0000FF"

    val headColor = localStorage.getItem("character_head$suffix") ?: defaultHead
    val bodyColor = localStorage.getItem("character_body$suffix") ?: if (playerNumber == 1) defaultP1Body else defaultP2Body
    val armsColor = localStorage.getItem("character_arms$suffix") ?: defaultArms
    val legsColor = localStorage.getItem("character_legs$suffix") ?: defaultLegs

    return CharacterColors(headColor, bodyColor, armsColor, legsColor)
}

data class CharacterColors(
    val head: String = "#FFD590",
    val body: String = "#FF0000",
    val arms: String = "#FFD590",
    val legs: String = "#0000FF",
)

fun saveCharacterColors(
    colors: CharacterColors,
    playerNumber: Int = 1,
) {
    val suffix = "_p$playerNumber"

    localStorage.setItem("character_head$suffix", colors.head)
    localStorage.setItem("character_body$suffix", colors.body)
    localStorage.setItem("character_arms$suffix", colors.arms)
    localStorage.setItem("character_legs$suffix", colors.legs)
}

fun colorsMatch(
    head1: String,
    head2: String,
    body1: String,
    body2: String,
    arms1: String,
    arms2: String,
    legs1: String,
    legs2: String,
): Boolean = head1 == head2 && body1 == body2 && arms1 == arms2 && legs1 == legs2

fun generateDifferentColors(player1Colors: CharacterColors): CharacterColors {
    val colorOptions = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#FFD590", "#000000")

    val bodyColor = colorOptions.firstOrNull { it != player1Colors.body } ?: "#00FF00"
    val legsColor = colorOptions.firstOrNull { it != player1Colors.legs } ?: "#000000"

    return CharacterColors(
        head = player1Colors.head,
        body = bodyColor,
        arms = player1Colors.arms,
        legs = legsColor,
    )
}
