package xyz.malefic.hell.components.orders

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.fontWeight
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.util.OrderItem
import xyz.malefic.hell.util.RecipeAction

@Composable
fun OrderItemView(
    item: OrderItem,
    completedActions: Map<String, Int>,
    onActionStart: (item: OrderItem, action: RecipeAction) -> Unit,
    canStartAction: (action: RecipeAction, item: OrderItem) -> Boolean,
) {
    Div({ style { marginBottom(10.px) } }) {
        H4({
            style {
                fontWeight(FontWeight.Bold)
                fontSize(1.em)
                marginBottom(2.px)
            }
        }) { Text(item.name) }
        item.actions.forEachIndexed { idx, action ->
            OrderActionButton(
                item = item,
                action = action,
                actionIdx = idx,
                completedActions = completedActions,
                onActionStart = onActionStart,
                canStartAction = canStartAction,
            )
        }
    }
}
