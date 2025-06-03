package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toAttrs
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.components.FooterBar
import xyz.malefic.hell.components.OptionsButton
import xyz.malefic.hell.components.WiiButton
import xyz.malefic.hell.components.WiiChannel
import xyz.malefic.hell.styles.WiiHomeStyles
import kotlin.js.Date

@Page
@Composable
@Layout(".components.layouts.PageLayout")
fun HomePage() {
    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }
    var highestUnlockedLevel by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    Div(WiiHomeStyles.container.toAttrs()) {
        Div(WiiHomeStyles.content.toAttrs()) {
            Div(WiiHomeStyles.sidebarLeft.toAttrs()) {
                WiiButton()
            }

            Div(WiiHomeStyles.channelGrid.toAttrs()) {
                SimpleGrid(
                    numColumns(base = 4),
                ) {
                    (1..12).forEach { i ->
                        if (i <= highestUnlockedLevel) {
                            WiiChannel(
                                "Level $i",
                                when (i) {
                                    1, 8, 12 -> "blue"
                                    2 -> "green"
                                    3 -> "yellow"
                                    4, 6, 10 -> "white"
                                    5, 9 -> "red"
                                    7, 11 -> "orange"
                                    else -> "lightgray"
                                },
                            ) {
                                when (i) {
                                    1 -> window.location.href = "/kitchen1"

                                    2 -> { // Navigate to Level 2
                                    }

                                    3 -> { // Navigate to Level 3
                                    }

                                    4 -> { // Navigate to Level 4
                                    }

                                    5 -> { // Navigate to Level 5
                                    }

                                    6 -> { // Navigate to Level 6
                                    }

                                    7 -> { // Navigate to Level 7
                                    }

                                    8 -> { // Navigate to Level 8
                                    }

                                    9 -> { // Navigate to Level 9
                                    }

                                    10 -> { // Navigate to Level 10
                                    }

                                    11 -> { // Navigate to Level 11
                                    }

                                    else -> { // Navigate to Level 12
                                    }
                                }
                            }
                        } else {
                            EmptyChannel()
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

private fun updateDateTime(update: (String, String) -> Unit) {
    val updateTime = {
        val date = js("new Date()") as Date
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

@Composable
fun EmptyChannel() = WiiChannel("Empty Channel", "darkgray")
