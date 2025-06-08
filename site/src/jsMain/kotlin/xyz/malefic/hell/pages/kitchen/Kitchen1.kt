package xyz.malefic.hell.pages.kitchen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.components.Pext
import xyz.malefic.hell.components.orders.OrderView
import xyz.malefic.hell.components.player.KeyBindings
import xyz.malefic.hell.components.player.Player
import xyz.malefic.hell.components.player.rememberPlayerPosition
import xyz.malefic.hell.services.updateDateTime
import xyz.malefic.hell.styles.KitchenStyles
import xyz.malefic.hell.util.CollisionObject
import xyz.malefic.hell.util.Order
import xyz.malefic.hell.util.OrderItem
import xyz.malefic.hell.util.Quadruple
import xyz.malefic.hell.util.RecipeAction
import xyz.malefic.hell.util.Recipes.salad
import xyz.malefic.hell.util.Recipes.soup
import xyz.malefic.hell.util.collide
import kotlin.math.sqrt

@Page("1")
@Composable
fun Kitchen1(ctx: PageContext) {
    var currentTime by remember { mutableStateOf("") }
    var currentDay by remember { mutableStateOf("") }
    val hasSecondPlayer by remember { mutableStateOf(localStorage.getItem("has_second_player") == "true") }

    val collisionObjects = mutableListOf<CollisionObject>()

    CollisionInit(collisionObjects)

    val characterPosition1 =
        rememberPlayerPosition(
            collisionObjects,
            400,
            300,
            KeyBindings("ArrowUp", "ArrowDown", "ArrowLeft", "ArrowRight"),
        )

    val characterPosition2 =
        rememberPlayerPosition(
            collisionObjects,
            300,
            300,
            KeyBindings("w", "s", "a", "d"),
        ).takeIf { hasSecondPlayer }

    LaunchedEffect(Unit) {
        updateDateTime { time, day ->
            currentTime = time
            currentDay = day
        }
    }

    val sampleOrder =
        remember {
            Order(
                "Order#1",
                listOf(
                    salad,
                    soup,
                ),
            )
        }

    var completedActions by remember { mutableStateOf(mapOf<String, Int>()) }
    var actionInProgress by remember { mutableStateOf<Pair<String, Int>?>(null) }
    var actionTimer by remember { mutableStateOf(0) }

    fun getStationCollisionObject(station: String): CollisionObject? =
        when (station) {
            "Counter" -> collisionObjects.getOrNull(0)
            "Stove" -> collisionObjects.getOrNull(1)
            else -> null
        }

    fun isColliding(
        pos: Pair<Int, Int>,
        rect: Quadruple<Int, Int, Int, Int>,
    ): Boolean {
        val (x, y) = pos
        val (rx, ry, rw, rh) = rect
        return x in rx..(rx + rw) && y in ry..(ry + rh)
    }

    fun getPlayerPos(): Pair<Int, Int> = characterPosition1.x to characterPosition1.y

    fun canStartAction(
        action: RecipeAction,
        item: OrderItem,
    ): Boolean {
        val logger = Logger.withTag("Kitchen1")
        val stationObj = getStationCollisionObject(action.station)
        logger.d {
            "canStartAction: action=${action.name}, station=${action.station}, player=(${characterPosition1.x},${characterPosition1.y}), stationObj=$stationObj"
        }
        if (stationObj == null) {
            logger.w { "canStartAction: No collision object found for station: ${action.station}" }
            return false
        }
        val idx = item.actions.indexOf(action) + 1
        return when {
            completedActions[item.name] == idx - 1 || idx == 1 -> {
                val pxRange = 200
                val playerCenterX = characterPosition1.x + 25
                val playerCenterY = characterPosition1.y + 35
                val stationCenterX = stationObj.x + stationObj.width / 2
                val stationCenterY = stationObj.y + stationObj.height / 2
                val dx = playerCenterX - stationCenterX
                val dy = playerCenterY - stationCenterY
                val distance = sqrt((dx * dx + dy * dy).toDouble())
                logger.d {
                    "canStartAction: playerCenter=($playerCenterX,$playerCenterY), stationCenter=($stationCenterX,$stationCenterY), distance=$distance, pxRange=$pxRange, withinRange=${distance <= pxRange}"
                }
                distance <= pxRange
            }
            else -> {
                logger.d {
                    "canStartAction: Action not ready, completedActions=${completedActions[item.name]}, idx=$idx, and issue is ${
                        "idx not right".takeIf {
                            completedActions[item.name] != idx
                        } ?: "not first action" }"
                }
                false
            }
        }
    }

    fun startAction(
        item: OrderItem,
        action: RecipeAction,
    ) {
        if (!canStartAction(action, item)) return
        val idx = item.actions.indexOf(action)
        if (actionInProgress == null) {
            actionInProgress = item.name to idx
            actionTimer = action.durationSeconds
        }
    }

    LaunchedEffect(actionInProgress) {
        val (itemName, _) = actionInProgress ?: return@LaunchedEffect
        while (actionTimer > 0) {
            delay(1000)
            actionTimer--
        }
        completedActions =
            completedActions.toMutableMap().apply {
                put(itemName, (get(itemName) ?: 0) + 1)
            }
        actionInProgress = null
    }

    Div(KitchenStyles.container.toAttrs()) {
        Button(
            KitchenStyles.leave.toAttrs {
                onClick {
                    ctx.router.navigateTo("/channel/1")
                }
            },
        ) {
            Text("Leave")
        }

        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
                alignItems("flex-start")
                width(100.percent)
            }
        }) {
            OrderView(
                order = sampleOrder,
                completedActions = completedActions,
                onActionStart = ::startAction,
                canStartAction = ::canStartAction,
            ) {
                marginTop(175.px)
                marginLeft(200.px)
            }

            Div(KitchenStyles.content.toAttrs()) {
                Div(KitchenStyles.kitchenContainer.toAttrs()) {
                    Div(KitchenStyles.floor.toAttrs())

                    Counter(collisionObjects)
                    Stove(collisionObjects)

                    Player(characterPosition1, playerId = 1)
                    characterPosition2?.let {
                        Player(characterPosition2, playerId = 2)
                    }
                }
            }
        }

        actionInProgress?.let { (itemName, actionIdx) ->
            val action = sampleOrder.items.first { it.name == itemName }.actions[actionIdx]
            Div({ style { color(Color.red) } }) {
                Text("${action.name} in progress: $actionTimer s left")
            }
        }

        KitchenFooter(
            "Use arrow keys to move the Mii character around the kitchen".takeUnless { hasSecondPlayer }
                ?: "Use the arrow keys and WASD to move the Mii character around the kitchen",
            currentTime,
            currentDay,
        )
    }
}

@Composable
private fun CollisionInit(collisionObjects: MutableList<CollisionObject>) {
    Div({ style { display(DisplayStyle.None) } }) {
        Counter(collisionObjects)
        Stove(collisionObjects)
    }
}

@Composable
private fun Counter(collisionObjects: MutableList<CollisionObject>) {
    Div(
        KitchenStyles.counter
            .collide(100, 100, 200, 100, collisionObjects)
            .toAttrs(),
    ) {
        Div(KitchenStyles.cuttingBoard.toAttrs())
    }
}

@Composable
private fun Stove(collisionObjects: MutableList<CollisionObject>) =
    Div(
        KitchenStyles.stove
            .collide(500, 100, 180, 120, collisionObjects)
            .toAttrs(),
    ) {
        DoubleBurner()
        TallBurner()
        DoubleBurner()
    }

@Composable
fun DoubleBurner() {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            height(100.percent)
        }
    }) {
        Div(KitchenStyles.burner.toAttrs())
        Div(KitchenStyles.burner.toAttrs())
    }
}

@Composable
private fun TallBurner() {
    Div({
        style {
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            height(100.percent)
        }
    }) {
        Div(KitchenStyles.burnerTall.toAttrs())
    }
}

@Composable
fun KitchenFooter(
    instructionText: String,
    currentTime: String,
    currentDay: String,
) {
    Box(KitchenStyles.footer.toModifier()) {
        Pext(instructionText, KitchenStyles.instructions)

        Column(
            Modifier.align(Alignment.CenterEnd),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Pext(currentTime, KitchenStyles.clock)
            Pext(currentDay, KitchenStyles.date)
        }
    }
}
