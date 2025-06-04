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
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.name
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import com.varabyte.kobweb.compose.css.Cursor
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.KeyBindings
import xyz.malefic.hell.components.Player
import xyz.malefic.hell.components.PlayerPosition
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
    val hasSecondPlayer by remember { mutableStateOf(localStorage.getItem("has_second_player") == "true") }

    val collisionObjects = remember { mutableListOf<CollisionObject>() }
    collisionObjects.clear()

    val keyBindingsP1 = KeyBindings(up = "ArrowUp", down = "ArrowDown", left = "ArrowLeft", right = "ArrowRight")
    val characterPosition1 =
        rememberPlayerPosition(
            collisionObjects = collisionObjects,
            initialX = 400,
            initialY = 300,
            keyBindings = keyBindingsP1,
        )

    val characterPosition2: PlayerPosition? = if (hasSecondPlayer) {
        val keyBindingsP2 = KeyBindings(up = "w", down = "s", left = "a", right = "d")
        rememberPlayerPosition(
            collisionObjects = collisionObjects, 
            initialX = 300, 
            initialY = 300,
            keyBindings = keyBindingsP2,
        )
    } else {
        null
    }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    Div(KitchenStyles.container.toAttrs()) {
        Button(
            attrs = Modifier
                .position(Position.Absolute)
                .top(20.px)
                .left(20.px)
                .zIndex(100)
                .padding(topBottom = 8.px, leftRight = 16.px)
                .backgroundColor(Color.rgb(34, 34, 34))
                .color(Colors.White)
                .borderRadius(8.px)
                .border(width = 0.px)
                .boxShadow(offsetX = 0.px, offsetY = 2.px, blurRadius = 4.px, color = rgba(0, 0, 0, 0.3))
                .cursor(Cursor.Pointer)
                .onClick { window.location.href = "/" }
                .toAttrs()
        ) {
            Text("Leave")
        }

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

                Player(characterPosition1, playerId = 1)
                if (characterPosition2 != null) {
                    Player(characterPosition2, playerId = 2)
                }
            }
        }

        Box(KitchenStyles.footer.toModifier()) {
            P(
                Modifier
                    .classNames(KitchenStyles.instructions.name)
                    .toAttrs(),
            ) {
                val instructionText = if (hasSecondPlayer) {
                    "Use WASD and the arrow keys to move the Mii characters around the kitchen"
                } else {
                    "Use arrow keys to move the Mii character around the kitchen"
                }
                Text(instructionText)
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
