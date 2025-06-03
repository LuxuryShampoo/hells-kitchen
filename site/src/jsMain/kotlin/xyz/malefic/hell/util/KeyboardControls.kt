package xyz.malefic.hell.util

@OptIn(ExperimentalJsExport::class)
@JsExport
fun setupKeyboardControls(
    @Suppress("kotlin:S1172") onKeyPress: (String) -> Unit,
) {
    js(
        """
        document.addEventListener('keydown', function(event) {
            if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(event.code)) {
                event.preventDefault();
                onKeyPress(event.code);
            }
        });
    """,
    )
}

fun isCollision(
    x: Int,
    y: Int,
    objects: List<CollisionObject>,
    characterSize: Int = 30,
): Boolean {
    for (obj in objects) {
        if (
            x < obj.x + obj.width &&
            x + characterSize > obj.x &&
            y < obj.y + obj.height &&
            y + characterSize > obj.y
        ) {
            return true
        }
    }
    return false
}

data class CollisionObject(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
)
