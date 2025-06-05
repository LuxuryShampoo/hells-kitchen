package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme

@Composable
fun WiiChannel(
    text: String,
    isDisabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    val channelBackgroundColor: Color
    val channelTextColor: Color

    if (isDisabled) {
        channelBackgroundColor = Color.rgb(0x44, 0x44, 0x44)
        channelTextColor = Colors.LightGray
    } else {
        when (LocalAppTheme.current) {
            AppTheme.DAY_SUNNY -> {
                channelBackgroundColor = Color.rgb(255, 215, 0)
                channelTextColor = Color.rgb(70, 70, 70)
            }
            AppTheme.DAY_CLOUDY -> {
                channelBackgroundColor = Colors.LightGray
                channelTextColor = Color.rgb(80, 80, 80)
            }
            AppTheme.NIGHT -> {
                channelBackgroundColor = Colors.WhiteSmoke
                channelTextColor = Color.rgb(80, 80, 80)
            }
        }
    }

    Box(
        Modifier
            .width(200.px)
            .height(140.px)
            .margin(10.px)
            .borderRadius(12.px)
            .backgroundColor(channelBackgroundColor)
            .cursor(if (isDisabled) Cursor.NotAllowed else Cursor.Pointer)
            .onClick { if (!isDisabled) onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Span(
            Modifier
                .color(channelTextColor)
                .fontWeight(FontWeight.Bold)
                .fontSize(18.px)
                .toAttrs(),
        ) {
            Text(text)
        }
    }
}
