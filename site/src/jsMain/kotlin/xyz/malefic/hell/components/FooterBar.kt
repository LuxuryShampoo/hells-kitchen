package xyz.malefic.hell.components

import androidx.compose.runtime.*
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
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles
import xyz.malefic.hell.theme.AppTheme
import xyz.malefic.hell.theme.LocalAppTheme
import xyz.malefic.hell.services.WeatherService
import xyz.malefic.hell.services.WeatherData

@Composable
fun FooterBar(
    time: String,
    date: String,
) {
    val currentTheme = LocalAppTheme.current
    val weatherDataResult = WeatherService.weatherDataResult
    val isLoadingWeather = WeatherService.isLoadingWeather

    val footerBackgroundColor = when (currentTheme) {
        AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(200, 220, 255) // Light Sky Blueish for all day types
        AppTheme.NIGHT -> Color.rgb(30, 40, 70)   // Dark Blue/Gray
    }

    val clockColor = when (currentTheme) {
        AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(80, 80, 80) // Dark gray for all day types
        AppTheme.NIGHT -> Colors.WhiteSmoke
    }
    val dateAndDetailColor = when (currentTheme) {
        AppTheme.DAY_SUNNY, AppTheme.DAY_CLOUDY -> Color.rgb(100, 100, 100) // Dark gray for all day types
        AppTheme.NIGHT -> Colors.WhiteSmoke
    }
    // Error color remains constant for now
    val errorColor = Color.rgb(0xF0, 0x80, 0x80)

    Box(
        modifier = WiiHomeStyles.footer.toModifier()
            .fillMaxWidth()
            .backgroundColor(footerBackgroundColor) // Apply themed background
            .padding(leftRight = 10.px, topBottom = 5.px),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            P(
                attrs = WiiHomeStyles.clock.toModifier()
                    .color(clockColor) // Apply themed color
                    .textAlign(TextAlign.Center)
                    .toAttrs(),
            ) {
                Text(time)
            }
            P(
                attrs = WiiHomeStyles.date.toModifier()
                    .color(dateAndDetailColor) // Apply themed color
                    .textAlign(TextAlign.Center)
                    .toAttrs(),
            ) {
                Text(date)
            }
        }

        Column(
            modifier = Modifier.align(Alignment.CenterEnd)
                .margin(right = 20.px),
            horizontalAlignment = Alignment.End,
        ) {
            when {
                isLoadingWeather -> {
                    P(
                        attrs = Modifier
                            .color(dateAndDetailColor) // Apply themed color for loading text
                            .fontSize(16.px)
                            .margin(0.px)
                            .toAttrs()
                    ) {
                        Text("Loading weather...")
                    }
                }
                weatherDataResult?.isSuccess == true -> {
                    val data = weatherDataResult!!.getOrNull()!!
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Img(src = data.iconUrl, attrs = {
                            style {
                                width(60.px)
                                height(60.px)
                                marginRight(8.px)
                            }
                        })
                        Column { 
                            P(
                                attrs = Modifier
                                    .color(clockColor) // Themed color for temperature (like clock)
                                    .fontSize(30.px)
                                    .fontWeight(FontWeight.Bold)
                                    .margin(0.px)
                                    .toAttrs(),
                            ) {
                                Text("${data.temperature}°C")
                            }
                            P(
                                attrs = Modifier
                                    .color(dateAndDetailColor) // Themed color for description
                                    .fontSize(20.px)
                                    .margin(top = 0.px, bottom = 0.px)
                                    .toAttrs(),
                            ) {
                                Text(data.description)
                            }
                        }
                    }
                }
                weatherDataResult?.isFailure == true -> {
                    P(
                        attrs = Modifier
                            .color(errorColor) // Error color remains constant
                            .fontSize(16.px)
                            .margin(0.px)
                            .toAttrs(),
                    ) {
                        Text("Weather unavailable")
                    }
                    console.error("[FooterBar] Weather fetch failed (from service): ${weatherDataResult.exceptionOrNull()?.message}")
                }
            }
        }
    }
}
