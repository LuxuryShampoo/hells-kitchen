package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.toAttrs
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiChannelStyles

@Page("/channel/{level}")
@Composable
@Layout(".components.layouts.PageLayout")
fun ChannelPage(ctx: PageContext) {
    // Get the level parameter from the path, default to 1 if not provided
    val level = ctx.route.params["level"]?.toIntOrNull() ?: 1

    Div(WiiChannelStyles.container.toAttrs()) {
        // Background with cloud pattern
        Div(WiiChannelStyles.background.toAttrs()) {
            // Main content area
            Div(WiiChannelStyles.content.toAttrs()) {
                // Shopping bag icon and title section
                Div(WiiChannelStyles.headerSection.toAttrs()) {
                    Div(WiiChannelStyles.iconContainer.toAttrs()) {
                        // Wii shopping bags icon
                        Div(WiiChannelStyles.shoppingBag.toAttrs()) {
                            Text("Wii")
                        }
                        Div(WiiChannelStyles.shoppingBagSecondary.toAttrs()) {
                            Text("Wii")
                        }
                    }

                    // Title
                    Div(WiiChannelStyles.title.toAttrs()) {
                        Text("Chapter $level")
                    }
                }

                // Navigation arrows
                Div(WiiChannelStyles.leftArrow.toAttrs()) {
                    Text("❮")
                }

                Div(WiiChannelStyles.rightArrow.toAttrs()) {
                    Text("❯")
                }
            }

            // Bottom navigation bar
            Div(WiiChannelStyles.bottomBar.toAttrs()) {
                Button(
                    WiiChannelStyles.navButton.toAttrs {
                        onClick {
                            window.location.href = "/"
                        }
                    },
                ) {
                    Text("Wii Menu")
                }

                Button(
                    WiiChannelStyles.navButton.toAttrs {
                        onClick {
                            window.location.href = "/kitchen$level"
                        }
                    },
                ) {
                    Text("Start")
                }
            }
        }
    }
}
