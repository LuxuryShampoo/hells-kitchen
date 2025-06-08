package xyz.malefic.hell.util

object Recipes {
    val salad =
        OrderItem(
            "Salad",
            listOf(
                RecipeAction("Chop", "Counter", 3),
                RecipeAction("Cook", "Stove", 5),
            ),
        )
    val soup =
        OrderItem(
            "Soup",
            listOf(
                RecipeAction("Chop", "Counter", 3),
                RecipeAction("Boil", "Stove", 5),
            ),
        )
    val steak =
        OrderItem(
            "Steak",
            listOf(
                RecipeAction("Season", "Counter", 2),
                RecipeAction("Grill", "Grill", 8),
                RecipeAction("Plate", "Counter", 2),
            ),
        )
    val pasta =
        OrderItem(
            "Pasta",
            listOf(
                RecipeAction("Boil", "Stove", 10),
                RecipeAction("Mix", "Counter", 3),
                RecipeAction("Plate", "Counter", 1),
            ),
        )
    val sandwich =
        OrderItem(
            "Sandwich",
            listOf(
                RecipeAction("Slice", "Counter", 2),
                RecipeAction("Assemble", "Counter", 4),
                RecipeAction("Cut", "Counter", 1),
            ),
        )
    val cake =
        OrderItem(
            "Cake",
            listOf(
                RecipeAction("Mix", "Counter", 5),
                RecipeAction("Bake", "Stove", 30),
                RecipeAction("Decorate", "Counter", 5),
            ),
        )
    val smoothie =
        OrderItem(
            "Smoothie",
            listOf(
                RecipeAction("Chop", "Counter", 2),
                RecipeAction("Blend", "Blender", 4),
                RecipeAction("Serve", "Counter", 1),
            ),
        )
}
