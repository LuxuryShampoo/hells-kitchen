package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.GeneralKind
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun Pext(
    text: String,
    style: CssStyle<GeneralKind> = emptyCss,
    modifier: Modifier.() -> Modifier = { this },
) = P(style.toModifier().modifier().toAttrs()) { Text(text) }

val emptyCss = CssStyle { base { Modifier } }
