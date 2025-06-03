package xyz.malefic.hell.util

@OptIn(ExperimentalJsExport::class)
@JsExport
fun setupKeyboardControls(onKeysPressed: (Set<String>) -> Unit) {
    @Suppress("UnusedVariable", "kotlin:S1481")
    js(
        """
        window._kobweb_pressedKeys = new Set();
        document.addEventListener('keydown', function(event) {
            if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(event.code)) {
                event.preventDefault();
                window._kobweb_pressedKeys.add(event.code);
            }
        });
        document.addEventListener('keyup', function(event) {
            window._kobweb_pressedKeys.delete(event.code);
        });
        function gameLoop() {
            window._kobweb_keysArray = Array.from(window._kobweb_pressedKeys);
            requestAnimationFrame(gameLoop);
        }
        gameLoop();
        """,
    )

    fun getPressedKeys(): Set<String> = js("window._kobweb_keysArray ? window._kobweb_keysArray : []").unsafeCast<Array<String>>().toSet()
    kotlinx.browser.window.setInterval({
        onKeysPressed(getPressedKeys())
    }, 16) // ~60fps
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
