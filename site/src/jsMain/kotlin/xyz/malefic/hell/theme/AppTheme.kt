package xyz.malefic.hell.theme

import androidx.compose.runtime.*
import kotlinx.browser.window
import xyz.malefic.hell.services.WeatherService // Import WeatherService
import xyz.malefic.hell.services.WeatherData // Import WeatherData

enum class AppTheme {
    DAY_SUNNY,
    DAY_CLOUDY,
    NIGHT
}

val LocalAppTheme = compositionLocalOf<AppTheme> { AppTheme.DAY_SUNNY } // Default to DAY_SUNNY

@Composable
fun rememberCurrentAppTheme(): AppTheme {
    // Observe weather data for theme decisions
    val weatherResult = WeatherService.weatherDataResult

    return remember(weatherResult) { // Re-calculate when weatherResult changes
        val currentHour = js("new Date().getHours()").unsafeCast<Int>()
        
        if (currentHour >= 6 && currentHour < 19) { // Daytime: 6 AM to 7 PM
            val weatherData = weatherResult?.getOrNull()
            if (weatherData != null && weatherData.cloudCover != null) {
                if (weatherData.cloudCover > 60) { // Example: more than 60% cloud cover
                    AppTheme.DAY_CLOUDY
                } else {
                    AppTheme.DAY_SUNNY
                }
            } else {
                // Default to sunny if weather data not available or no cloud info during day
                AppTheme.DAY_SUNNY 
            }
        } else { // Nighttime
            AppTheme.NIGHT
        }
    }
} 