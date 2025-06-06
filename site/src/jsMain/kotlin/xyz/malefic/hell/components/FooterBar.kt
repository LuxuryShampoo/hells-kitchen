package xyz.malefic.hell.components

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Img
import xyz.malefic.hell.services.WeatherService
import xyz.malefic.hell.styles.WiiHomeStyles
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme

@Composable
fun FooterBar(
    time: String,
    date: String,
) {
    val currentTheme = LocalAppTheme.current
    val weatherDataResult = WeatherService.weatherDataResult
    val isLoadingWeather = WeatherService.isLoadingWeather

    val footerBackgroundColor =
        when (currentTheme) {
            AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(200, 220, 255)
            AppTheme.NIGHT -> Color.rgb(30, 40, 70) // Dark Blue/Gray
        }

    val clockColor =
        when (currentTheme) {
            AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(80, 80, 80)
            AppTheme.NIGHT -> Colors.WhiteSmoke
        }
    val dateAndDetailColor =
        when (currentTheme) {
            AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(100, 100, 100)
            AppTheme.NIGHT -> Colors.WhiteSmoke
        }
    val errorColor = Color.rgb(0xF0, 0x80, 0x80)

    Box(
        modifier =
            WiiHomeStyles.footer
                .toModifier()
                .fillMaxWidth()
                .backgroundColor(footerBackgroundColor)
                .padding(leftRight = 10.px, topBottom = 5.px),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Pext(time, WiiHomeStyles.clock) {
                color(clockColor).textAlign(TextAlign.Center)
            }
            Pext(date, WiiHomeStyles.date) {
                color(dateAndDetailColor).textAlign(TextAlign.Center)
            }
        }

        Column(
            Modifier
                .align(Alignment.CenterEnd)
                .margin(right = 20.px),
            horizontalAlignment = Alignment.End,
        ) {
            when {
                isLoadingWeather -> {
                    Pext("Loading weather...") {
                        color(dateAndDetailColor)
                        fontSize(16.px)
                        margin(0.px)
                    }
                }
                weatherDataResult?.isSuccess == true -> {
                    val data = weatherDataResult.getOrNull()!!
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Img(src = data.iconUrl, attrs = {
                            style {
                                width(60.px)
                                height(60.px)
                                marginRight(8.px)
                            }
                        })
                        Column {
                            Pext("${data.temperature}°C") {
                                color(clockColor)
                                fontSize(30.px)
                                fontWeight(FontWeight.Bold)
                                margin(0.px)
                            }
                            Pext(data.description) {
                                color(dateAndDetailColor)
                                fontSize(16.px)
                                margin(0.px)
                            }
                        }
                    }
                }
                weatherDataResult?.isFailure == true -> {
                    Pext("Weather unavailable") {
                        color(errorColor)
                        fontSize(16.px)
                        margin(0.px)
                    }
                    Logger.withTag("FooterBar").e("Weather fetch failed (from service): ${weatherDataResult.exceptionOrNull()?.message}")
                }
            }
        }
    }
}
