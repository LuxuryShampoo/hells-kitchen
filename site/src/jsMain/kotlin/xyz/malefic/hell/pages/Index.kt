package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.components.FooterBar
import xyz.malefic.hell.components.WiiButton
import xyz.malefic.hell.components.WiiChannel
import xyz.malefic.hell.components.layouts.PageLayout
import xyz.malefic.hell.styles.WiiHomeStyles
import kotlin.js.Date

@Page
@Composable
fun HomePage() {
    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    PageLayout {
        Div(WiiHomeStyles.container.toModifier().toAttrs()) {
            // Main Content
            Div(WiiHomeStyles.content.toModifier().toAttrs()) {
                // Left side Wii Button
                Div(WiiHomeStyles.sidebarLeft.toModifier().toAttrs()) {
                    WiiButton()
                }

                // Main Grid for Channels
                Div(WiiHomeStyles.channelGrid.toModifier().toAttrs()) {
                    SimpleGrid(
                        numColumns(base = 4),
//                        breakpoint = Breakpoint.MD,
                    ) {
                        WiiChannel("Mii Channel", "blue")
                        WiiChannel("Shop Channel", "green")
                        WiiChannel("Photo Channel", "yellow")
                        WiiChannel("Wii Sports", "white")
                        WiiChannel("News Channel", "red")
                        WiiChannel("Weather Channel", "white")
                        WiiChannel("Internet Channel", "orange")
                        WiiChannel("Disc Channel", "blue")
                    }
                }

                // Right side buttons/controls
                Div(WiiHomeStyles.sidebarRight.toModifier().toAttrs()) {
                    // Right side buttons would go here
                }
            }

            // Footer bar with time and date
            FooterBar(currentTime, currentDay)
        }
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
    window.setInterval(updateTime, 60000) // Update every minute
}
