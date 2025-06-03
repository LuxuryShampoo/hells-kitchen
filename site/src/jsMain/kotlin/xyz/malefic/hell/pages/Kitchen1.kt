package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.name
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.Player
import xyz.malefic.hell.components.rememberPlayerPosition
import xyz.malefic.hell.styles.KitchenStyles
import xyz.malefic.hell.util.CollisionObject
import xyz.malefic.hell.util.collide

data class Point(
    val x: Int,
    val y: Int,
)

@Page("/kitchen1")
@Composable
fun Kitchen1() {
    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }

    val collisionObjects = remember { mutableListOf<CollisionObject>() }
    collisionObjects.clear()

    val characterPosition =
        rememberPlayerPosition(
            collisionObjects = collisionObjects,
        )

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    Div(KitchenStyles.container.toAttrs()) {
        Div(KitchenStyles.content.toAttrs()) {
            Div(KitchenStyles.kitchenContainer.toAttrs()) {
                Div(KitchenStyles.floor.toAttrs())

                Div(
                    KitchenStyles.counter
                        .collide(100, 100, 200, 100, collisionObjects)
                        .toAttrs(),
                ) {
                    Div(KitchenStyles.cuttingBoard.toAttrs())
                }

                Div(
                    KitchenStyles.stove
                        .collide(500, 100, 180, 120, collisionObjects)
                        .toAttrs(),
                ) {
                    @Composable
                    fun Burner() {
                        Div({
                            style {
                                display(DisplayStyle.Flex)
                                flexDirection(FlexDirection.Column)
                                justifyContent(JustifyContent.Center)
                                alignItems(AlignItems.Center)
                                height(100.percent)
                            }
                        }) {
                            Div(Modifier.classNames(KitchenStyles.burner.name).toAttrs())
                            Div(Modifier.classNames(KitchenStyles.burner.name).toAttrs())
                        }
                    }

                    Burner()
                    Div({
                        style {
                            display(DisplayStyle.Flex)
                            alignItems(AlignItems.Center)
                            height(100.percent)
                        }
                    }) {
                        Div(Modifier.classNames(KitchenStyles.burnerTall.name).toAttrs())
                    }
                    Burner()
                }

                Player(characterPosition)
            }
        }

        Box(KitchenStyles.footer.toModifier()) {
            P(
                Modifier
                    .classNames(KitchenStyles.instructions.name)
                    .toAttrs(),
            ) {
                Text("Use arrow keys to move the Mii character around the kitchen")
            }

            Column(
                Modifier.align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                P(
                    Modifier
                        .classNames(KitchenStyles.clock.name)
                        .toAttrs(),
                ) {
                    Text(currentTime)
                }
                P(
                    Modifier
                        .classNames(KitchenStyles.date.name)
                        .toAttrs(),
                ) {
                    Text(currentDay)
                }
            }
        }
    }
}

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
