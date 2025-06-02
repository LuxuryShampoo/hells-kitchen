package xyz.malefic.hell.styles.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.zIndex
import com.varabyte.kobweb.core.layout.Layout
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import kotlin.js.Promise

@JsNonModule
@JsModule("html2canvas")
external fun html2canvas(
    element: HTMLElement,
    options: dynamic = definedExternally,
): Promise<dynamic>

/**
 * Layout that pixelates all its content using html2canvas.
 *
 * Usage:
 * PixelatedLayout {
 *    // your entire site/page content here
 * }
 */
@Layout
@Composable
fun PixelatedLayout(content: @Composable () -> Unit) {
    val containerId = "pixelate-root"
    val canvasId = "pixelate-canvas"

    var hidden by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val screenWidth = window.innerWidth
        val screenHeight = window.innerHeight
        val pixelation = 0.25
        window.setTimeout({
            val container = document.getElementById(containerId) as? HTMLElement
            if (container != null && document.getElementById(canvasId) == null) {
                val options = js("{}")
                options.width = (screenWidth * pixelation).toInt()
                options.height = (screenHeight * pixelation).toInt()
                options.scale = 1
                html2canvas(container, options).then { canvas: HTMLCanvasElement ->
                    with(canvas) {
                        id = canvasId
                        style.width = "${screenWidth}px"
                        style.height = "${screenHeight}px"
                        style.imageRendering = "pixelated"
                        style.position = "fixed"
                        style.top = "0"
                        style.left = "0"
                        style.zIndex = "9999"
                        style.asDynamic().pointerEvents = "auto"
                        document.body?.appendChild(canvas)
                    }
                    window.setTimeout({
                        container.style.display = "none"
                        hidden = true
                    }, 50)
                }
            }
        }, 100)
    }

    Div(
        attrs = {
            id(containerId)
            style {
                width(100.vw)
                height(100.vh)
                display(DisplayStyle.Flex)
                property("justify-content", "center")
                property("align-items", "center")
                position(Position.Fixed)
                property("top", "0")
                property("left", "0")
                zIndex(0)
                background("white")
                if (hidden) display(DisplayStyle.None)
            }
        },
    ) {
        content()
    }
}
