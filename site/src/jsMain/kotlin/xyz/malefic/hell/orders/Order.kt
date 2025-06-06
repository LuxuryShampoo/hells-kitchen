package xyz.malefic.hell.orders

/**
 * Represents an action in a recipe, which is part of an order item.
 *
 * @property name The name of the action.
 * @property station The station where the action is performed.
 * @property durationSeconds The duration of the action in seconds.
 */
data class RecipeAction(
    val name: String,
    val station: String,
    val durationSeconds: Int,
)

/**
 * Represents an item in an order, which consists of multiple recipe actions.
 *
 * @property name The name of the order item.
 * @property actions A list of actions required to prepare the item.
 */
data class OrderItem(
    val name: String,
    val actions: List<RecipeAction>,
)

/**
 * Represents an order containing multiple items.
 *
 * @property id The unique identifier for the order.
 * @property items A list of items included in the order.
 */
data class Order(
    val id: String,
    val items: List<OrderItem>,
)
