package xyz.malefic.hell.orders

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.boxShadow
import com.varabyte.kobweb.compose.css.color
import com.varabyte.kobweb.compose.css.fontWeight
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
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

/**
 * A composable function that renders the view for an order.
 *
 * @param order The order to be displayed, containing its ID and items.
 * @param completedActions A map tracking the number of completed actions for each order item.
 *                         The key is the item name, and the value is the count of completed actions.
 * @param onActionStart A callback invoked when an action button is clicked.
 *                      Provides the corresponding order item and recipe action.
 */
@Composable
fun OrderView(
    order: Order,
    completedActions: Map<String, Int>,
    onActionStart: (item: OrderItem, action: RecipeAction) -> Unit,
    canStartAction: (action: RecipeAction) -> Boolean = { true },
) {
    Div({
        style {
            backgroundColor(Color.white)
            borderRadius(8.px)
            border {
                width(2.px)
                style(LineStyle.Dashed)
                color(Color("#bbb"))
            }
            boxShadow(0.px, 4.px, 16.px, 0.px, rgba(0, 0, 0, 0.08))
            padding(20.px)
            fontFamily("monospace")
            minWidth(220.px)
            maxWidth(260.px)
            margin(16.px)
        }
    }) {
        H3({
            style {
                fontWeight(FontWeight.Bold)
                fontSize(1.1.em)
            }
        }) { Text("Order Receipt") }
        Div({
            style {
                borderBottom(1.px, LineStyle.Dotted, Color("#bbb"))
                marginBottom(8.px)
            }
        }) {
            Text("#${order.id}")
        }
        order.items.forEach { item ->
            Div({ style { marginBottom(10.px) } }) {
                H4({
                    style {
                        fontWeight(FontWeight.Bold)
                        fontSize(1.em)
                        marginBottom(2.px)
                    }
                }) { Text(item.name) }
                item.actions.forEachIndexed { idx, action ->
                    val isCompleted = (completedActions[item.name] ?: 0) > idx
                    Div({
                        style {
                            display(DisplayStyle.Flex)
                            alignItems("center")
                            marginBottom(2.px)
                        }
                    }) {
                        Button(attrs = {
                            if (isCompleted || !canStartAction(action)) disabled()
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
            }
        }
    }
}
