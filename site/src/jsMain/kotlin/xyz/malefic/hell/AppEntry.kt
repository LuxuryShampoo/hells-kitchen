package xyz.malefic.hell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.vh
import xyz.malefic.hell.services.WeatherService
import xyz.malefic.hell.styles.WiiHomeStyles
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme
import xyz.malefic.hell.theme.rememberCurrentAppTheme
import kotlin.js.Date

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    val currentTheme = rememberCurrentAppTheme()
    var lastFetchedHour by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        WeatherService.initialFetch()
        lastFetchedHour = Date().getHours()
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60000)
            val currentHour = Date().getHours()
            if (currentHour != lastFetchedHour) {
                Logger.withTag("WeatherService").d("[AppEntry] New hour ($currentHour) detected. Refreshing weather.")
                WeatherService.refreshWeather()
                lastFetchedHour = currentHour
            }
        }
    }

    CompositionLocalProvider(LocalAppTheme provides currentTheme) {
        SilkApp {
            val appBackgroundModifier =
                WiiHomeStyles.container
                    .toModifier()
                    .then(
                        when (currentTheme) {
                            AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Modifier.backgroundColor(Color.rgb(135, 206, 235))
                            AppTheme.NIGHT -> Modifier.backgroundColor(Color.rgb(0, 0, 100))
                        },
                    )

            Surface(appBackgroundModifier.minHeight(100.vh)) {
                content()
            }
        }
    }
}
