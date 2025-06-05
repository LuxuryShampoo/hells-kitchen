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
import com.varabyte.kobweb.core.PageContext
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
fun HomePage(ctx: PageContext) {
    val currentTheme = LocalAppTheme.current

    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }
    var highestUnlockedLevel by remember {
        mutableStateOf(localStorage.getItem("highestUnlockedLevel")?.toIntOrNull() ?: 1)
    }
    var miiClicked by remember {
        mutableStateOf(localStorage.getItem("mii_clicked") == "true")
    }

    LaunchedEffect(highestUnlockedLevel) {
        localStorage.setItem("highestUnlockedLevel", highestUnlockedLevel.toString())
    }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    val wiiButtonBackgroundColor =
        when (currentTheme) {
            AppTheme.DAY_SUNNY -> Color.rgb(255, 215, 0)
            AppTheme.DAY_CLOUDY -> Colors.LightGray
            AppTheme.NIGHT -> Colors.WhiteSmoke
        }

    Div {
        Div(WiiHomeStyles.content.toAttrs()) {
            Div(WiiHomeStyles.sidebarLeft.toAttrs()) {
                WiiButton(
                    modifier = Modifier.backgroundColor(wiiButtonBackgroundColor),
                    isClicked = miiClicked,
                    ctx,
                ) {
                    miiClicked = true
                    localStorage.setItem("mii_clicked", "true")
                    ctx.router.navigateTo("/character-customize?player=1")
                }
            }

            Div(WiiHomeStyles.channelGrid.toAttrs()) {
                SimpleGrid(
                    numColumns(base = 4),
                ) {
                    (1..12).forEach { i ->
                        when {
                            i == 1 && !miiClicked -> {
                                Div(attrs = {
                                    title("Click the Mii to customize first")
                                }) {
                                    WiiChannel("Level $i", true)
                                }
                            }
                            i <= highestUnlockedLevel -> {
                                WiiChannel("Chapter $i") {
                                    ctx.router.navigateTo("/channel/$i")
                                }
                            }
                            i == highestUnlockedLevel + 1 -> {
                                WiiChannel("Coming Soon!", true)
                            }
                            else -> {
                                WiiChannel("", true)
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
