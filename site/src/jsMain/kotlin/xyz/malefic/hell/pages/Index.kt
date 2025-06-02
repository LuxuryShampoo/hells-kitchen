package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.graphics.Image

@Page
@Composable
@Layout(".styles.layouts.PixelatedLayout")
fun HomePage() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Image("https://gallery.malefic.xyz/photos/Ace%20Of%20Hearts/Hearts.png")
    }
}
