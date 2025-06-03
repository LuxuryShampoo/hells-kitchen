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
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.name
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.CollisionObject
import xyz.malefic.hell.isCollision
import xyz.malefic.hell.setupKeyboardControls
import xyz.malefic.hell.styles.KitchenStyles
import kotlin.js.Date

data class Point(
    val x: Int,
    val y: Int,
)

@Page("/kitchen1")
@Composable
fun Kitchen1() {
    var characterPosition by remember { mutableStateOf(Point(300, 250)) }
    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }

    // Define collision objects
    val collisionObjects =
        remember {
            listOf(
                // Counter
                CollisionObject(100, 100, 200, 100),
                // Stove
                CollisionObject(500, 100, 180, 120),
            )
        }

    // Update time
    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    // Set up keyboard controls
    LaunchedEffect(Unit) {
        setupKeyboardControls { keyCode ->
            val newX =
                when (keyCode) {
                    "ArrowLeft" -> characterPosition.x - 10
                    "ArrowRight" -> characterPosition.x + 10
                    else -> characterPosition.x
                }

            val newY =
                when (keyCode) {
                    "ArrowUp" -> characterPosition.y - 10
                    "ArrowDown" -> characterPosition.y + 10
                    else -> characterPosition.y
                }

            // Check bounds and collisions
            if (newX in 0..750 && newY in 0..350) {
                val tentativePos = Point(newX, newY)
                if (!isCollision(newX, newY, collisionObjects)) {
                    characterPosition = tentativePos
                }
            }
        }
    }

    Div(KitchenStyles.container.toAttrs()) {
        Div(KitchenStyles.content.toAttrs()) {
            Div(KitchenStyles.kitchenContainer.toAttrs()) {
                // Kitchen floor
                Div(KitchenStyles.floor.toAttrs())

                // Counter
                Div(KitchenStyles.counter.toAttrs()) {
                    // Cutting board
                    Div(KitchenStyles.cuttingBoard.toAttrs())
                }

                // Stove
                Div(KitchenStyles.stove.toAttrs()) {
                    // Burners
                    for (i in 0 until 4) {
                        val row = i / 2
                        val col = i % 2
                        Div(
                            attrs =
                                Modifier
                                    .classNames(KitchenStyles.burner.name)
                                    .position(Position.Absolute)
                                    .left((20 + col * 80).px)
                                    .top((20 + row * 60).px)
                                    .toAttrs(),
                        )
                    }
                }

                // Character
                Div(
                    attrs =
                        Modifier
                            .classNames(KitchenStyles.character.name)
                            .left(characterPosition.x.px)
                            .top(characterPosition.y.px)
                            .toAttrs(),
                ) {
                    // Character face
                    Div(
                        attrs =
                            Modifier
                                .classNames(KitchenStyles.characterFace.name)
                                .toAttrs(),
                    ) {
                        // Eyes
                        Div(
                            attrs =
                                Modifier
                                    .size(5.px, 5.px)
                                    .backgroundColor(com.varabyte.kobweb.compose.ui.graphics.Colors.White)
                                    .position(Position.Absolute)
                                    .left(3.px)
                                    .top(2.px)
                                    .borderRadius(50.percent)
                                    .toAttrs(),
                        )
                        Div(
                            attrs =
                                Modifier
                                    .size(5.px, 5.px)
                                    .backgroundColor(com.varabyte.kobweb.compose.ui.graphics.Colors.White)
                                    .position(Position.Absolute)
                                    .left(12.px)
                                    .top(2.px)
                                    .borderRadius(50.percent)
                                    .toAttrs(),
                        )
                    }
                }
            }
        }

        // Footer with instructions and time
        Box(KitchenStyles.footer.toModifier()) {
            P(
                attrs =
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

private fun updateDateTime(update: (String, String) -> Unit) {
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
