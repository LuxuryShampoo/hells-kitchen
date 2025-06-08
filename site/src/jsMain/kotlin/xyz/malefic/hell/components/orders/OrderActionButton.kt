package xyz.malefic.hell.components.orders

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.util.OrderItem
import xyz.malefic.hell.util.RecipeAction

@Composable
fun OrderActionButton(
    item: OrderItem,
    action: RecipeAction,
    actionIdx: Int,
    completedActions: Map<String, Int>,
    onActionStart: (item: OrderItem, action: RecipeAction) -> Unit,
    canStartAction: (action: RecipeAction, item: OrderItem) -> Boolean,
) {
    val isCompleted = (completedActions[item.name] ?: 0) > actionIdx
    Div({
        style {
            display(DisplayStyle.Flex)
            alignItems("center")
            marginBottom(2.px)
        }
    }) {
        Button(attrs = {
            if (isCompleted || !canStartAction(action, item)) disabled()
            style {
                fontFamily("monospace")
                fontSize(0.95.em)
                marginRight(6.px)
                backgroundColor(Color(if (isCompleted) "#e0ffe0" else "#f8f8f8"))
                borderRadius(4.px)
                border {
                    width(1.px)
                    style(LineStyle.Solid)
                    color(Color("#ccc"))
                }
                padding(2.px, 8.px)
            }
            onClick { onActionStart(item, action) }
        }) {
            Text("${action.name} @ ${action.station} (${action.durationSeconds}s)" + if (isCompleted) " ✓" else "")
        }
    }
}
