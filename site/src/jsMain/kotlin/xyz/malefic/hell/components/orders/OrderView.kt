package xyz.malefic.hell.components.orders

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.boxShadow
import com.varabyte.kobweb.compose.css.fontWeight
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.util.Order
import xyz.malefic.hell.util.OrderItem
import xyz.malefic.hell.util.RecipeAction

@Composable
fun OrderView(
    order: Order,
    completedActions: Map<String, Int>,
    onActionStart: (item: OrderItem, action: RecipeAction) -> Unit,
    canStartAction: (action: RecipeAction, item: OrderItem) -> Boolean,
    extraStyle: StyleScope.() -> Unit = {},
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
            extraStyle()
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
            OrderItemView(
                item = item,
                completedActions = completedActions,
                onActionStart = onActionStart,
                canStartAction = canStartAction,
            )
        }
    }
}
