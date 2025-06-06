package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.toAttrs
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiChannelStyles

@Page("/channel/{level}")
@Composable
@Layout(".components.layouts.PageLayout")
fun ChannelPage(ctx: PageContext) {
    val level = ctx.route.params["level"]?.toIntOrNull() ?: 1
    val highestUnlockedLevel by remember {
        mutableStateOf(localStorage.getItem("highestUnlockedLevel")?.toIntOrNull() ?: 1)
    }

    Div(WiiChannelStyles.container.toAttrs()) {
        Div(WiiChannelStyles.background.toAttrs()) {
            Div(WiiChannelStyles.content.toAttrs()) {
                Div(WiiChannelStyles.headerSection.toAttrs()) {
                    Div(WiiChannelStyles.iconContainer.toAttrs()) {
                        Div(WiiChannelStyles.shoppingBag.toAttrs()) {
                            Text("Wii")
                        }
                        Div(WiiChannelStyles.shoppingBagSecondary.toAttrs()) {
                            Text("Wii")
                        }
                    }

                    Div(WiiChannelStyles.title.toAttrs()) {
                        Text("Chapter $level")
                    }
                }

                Div(
                    WiiChannelStyles.leftArrow.toAttrs {
                        onClick { ctx.router.navigateTo("/channel/${(level - 1).coerceIn(1,highestUnlockedLevel)}") }
                    },
                ) {
                    Text("❮")
                }

                Div(
                    WiiChannelStyles.rightArrow.toAttrs {
                        onClick { ctx.router.navigateTo("/channel/${(level + 1).coerceIn(1,highestUnlockedLevel)}") }
                    },
                ) {
                    Text("❯")
                }
            }

            Div(WiiChannelStyles.bottomBar.toAttrs()) {
                Button(
                    WiiChannelStyles.navButton.toAttrs {
                        onClick { ctx.router.navigateTo("/") }
                    },
                ) {
                    Text("Wii Menu")
                }

                Button(
                    WiiChannelStyles.navButton.toAttrs {
                        onClick { ctx.router.navigateTo("/kitchen/$level") }
                    },
                ) {
                    Text("Start")
                }
            }
        }
    }
}
