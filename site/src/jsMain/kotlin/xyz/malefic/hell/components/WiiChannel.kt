package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme

@Composable
fun WiiChannel(
    text: String,
    colorInput: String,
    onClick: () -> Unit = {},
) {
    val currentTheme = LocalAppTheme.current
    val isDisabled = colorInput == "darkgray"

    val channelBackgroundColor: Color
    val channelTextColor: Color

    if (isDisabled) {
        channelBackgroundColor = Color.rgb(0x44, 0x44, 0x44) // #444444
        channelTextColor = Colors.LightGray
    } else {
        when (currentTheme) {
            AppTheme.DAY_SUNNY -> {
                channelBackgroundColor = Color.rgb(255, 215, 0) // Gold for Sun
                channelTextColor = Color.rgb(70, 70, 70)      // Dark Gray text for Sun
            }
            AppTheme.DAY_CLOUDY -> {
                channelBackgroundColor = Colors.LightGray      // Light gray for Cloudy Day
                channelTextColor = Color.rgb(80, 80, 80)     // Dark Gray text for Cloudy Day
            }
            AppTheme.NIGHT -> {
                channelBackgroundColor = Colors.WhiteSmoke         // WhiteSmoke for Night Cloud
                channelTextColor = Color.rgb(80, 80, 80)     // Dark Gray text for Night Cloud
            }
        }
    }

    Box(
        modifier =
            Modifier
                .width(140.px)
                .height(100.px)
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
