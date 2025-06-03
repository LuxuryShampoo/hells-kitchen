package xyz.malefic.hell

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
): Boolean {
    for (obj in objects) {
        if (x >= obj.x &&
            x < obj.x + obj.width &&
            y >= obj.y &&
            y < obj.y + obj.height
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
