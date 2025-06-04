package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toAttrs
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.components.FooterBar
import xyz.malefic.hell.components.OptionsButton
import xyz.malefic.hell.components.WiiButton
import xyz.malefic.hell.components.WiiChannel
import xyz.malefic.hell.styles.WiiHomeStyles
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme
import kotlin.js.Date

@Page
@Composable
@Layout(".components.layouts.PageLayout")
fun HomePage() {
    val currentTheme = LocalAppTheme.current

    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }
    var highestUnlockedLevel by remember { mutableStateOf(1) }
    var miiClicked by remember {
        mutableStateOf(localStorage.getItem("mii_clicked") == "true")
    }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    val wiiButtonBackgroundColor =
        when (currentTheme) {
            AppTheme.DAY_SUNNY -> Color.rgb(255, 215, 0) // Gold for Sun
            AppTheme.DAY_CLOUDY -> Colors.LightGray // Light gray for Cloudy Day
            AppTheme.NIGHT -> Colors.WhiteSmoke // WhiteSmoke for Night Cloud
        }

    Div {
        Div(WiiHomeStyles.content.toAttrs()) {
            Div(WiiHomeStyles.sidebarLeft.toAttrs()) {
                WiiButton(
                    modifier = Modifier.backgroundColor(wiiButtonBackgroundColor),
                    isClicked = miiClicked,
                    onClick = { miiClicked = true },
                )
            }

            Div(WiiHomeStyles.channelGrid.toAttrs()) {
                SimpleGrid(
                    numColumns(base = 4),
                ) {
                    (1..12).forEach { i ->
                        if (i == 1 && !miiClicked) {
                            // Level 1 is locked until Mii is clicked
                            WiiChannel(
                                "Level $i",
                                "darkgray", // This will use the darker grey in WiiChannel
                            ) {
                                // No action when locked
                            }
                        } else if (i <= highestUnlockedLevel) {
                            WiiChannel(
                                "Chapter $i",
                                when (i) {
                                    1, 8, 12 -> "blue"
                                    2 -> "green"
                                    3 -> "yellow"
                                    4, 6, 10 -> "white"
                                    5, 9 -> "red"
                                    7, 11 -> "orange"
                                    else -> "#5d5d5d" // Use #5d5d5d for any other unlocked levels
                                },
                            ) {
                                when (i) {
                                    1 -> window.location.href = "/kitchen1"
                                    2 -> { /* Navigate to Level 2 */ }
                                    3 -> { /* Navigate to Level 3 */ }
                                    4 -> { /* Navigate to Level 4 */ }
                                    5 -> { /* Navigate to Level 5 */ }
                                    6 -> { /* Navigate to Level 6 */ }
                                    7 -> { /* Navigate to Level 7 */ }
                                    8 -> { /* Navigate to Level 8 */ }
                                    9 -> { /* Navigate to Level 9 */ }
                                    10 -> { /* Navigate to Level 10 */ }
                                    11 -> { /* Navigate to Level 11 */ }
                                    else -> { /* Navigate to Level 12 */ }
                                }
                            }
                        } else if (i == highestUnlockedLevel + 1) {
                            WiiChannel(
                                "Coming Soon!",
                                "darkgray", // Dark gray for "Coming Soon!"
                            ) {
                                // No action for "Coming Soon!"
                            }
                        } else {
                            WiiChannel(
                                "", // No text for empty channels
                                "darkgray", // Use #5d5d5d for empty channels
                            ) {
                                // No action for empty channels
                            }
                        }
                    }
                }
            }

            Div(WiiHomeStyles.sidebarRight.toAttrs()) {
                OptionsButton()
            }
        }

        FooterBar(currentTime, currentDay)
    }
}

fun updateDateTime(update: (String, String) -> Unit) {
    val updateTime = {
        val date = Date()
        val hours = date.getHours().toString().padStart(2, '0')
        val minutes = date.getMinutes().toString().padStart(2, '0')
        val time = "$hours:$minutes"

        val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val dayOfWeek = days[date.getDay()]
        val month = (date.getMonth() + 1).toString()
        val dayOfMonth = date.getDate().toString()
        val day = "$dayOfWeek $month/$dayOfMonth"

        update(time, day)
    }

    updateTime()
    window.setInterval(updateTime, 60000)
}
