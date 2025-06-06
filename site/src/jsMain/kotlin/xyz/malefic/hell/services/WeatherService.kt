package xyz.malefic.hell.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch

data class WeatherData(
    val temperature: String,
    val description: String,
    val iconUrl: String,
    val cloudCover: Int? = null,
)

@OptIn(DelicateCoroutinesApi::class)
object WeatherService {
    private val logger = Logger.withTag("WeatherService")

    var weatherDataResult by mutableStateOf<Result<WeatherData>?>(null)
    var isLoadingWeather by mutableStateOf(false)

    fun initialFetch() {
        if (weatherDataResult == null && !isLoadingWeather) {
            logger.d("Initial weather fetch triggered.")
            fetch()
        }
    }

    fun refreshWeather() {
        logger.d("Refresh weather triggered.")
        fetch()
    }

    private fun fetch() {
        isLoadingWeather = true
        logger.d("fetchWeather Called - Using WeatherAPI.com")
        val navigatorDynamic = window.navigator.asDynamic()

        if (js(
                "typeof navigatorDynamic.geolocation === 'undefined' || typeof navigatorDynamic.geolocation.getCurrentPosition !== 'function'",
            )
        ) {
            logger.e("Geolocation API is not available.")
            weatherDataResult = Result.failure(Exception("Geolocation API not available"))
            isLoadingWeather = false
            return
        }

        val geolocation = navigatorDynamic.geolocation
        logger.d("Attempting to get current position (timeout: 10s, low accuracy)...")
        val options = js("{ enableHighAccuracy: false, timeout: 10000, maximumAge: 600000 }")

        geolocation.getCurrentPosition(
            successCallback = { positionDynamic ->
                logger.d("Geolocation success. Position: $positionDynamic")
                val coords = positionDynamic.coords
                if (coords == null || js("typeof coords.latitude === 'undefined' || typeof coords.longitude === 'undefined'")) {
                    logger.e("Geolocation coordinates are missing or invalid.")
                    weatherDataResult = Result.failure(Exception("Geolocation coordinates missing"))
                    isLoadingWeather = false
                    return@getCurrentPosition
                }
                val lat = coords.latitude
                val lon = coords.longitude
                fetchWeatherForCoords(lat, lon)
            },
            errorCallback = { errorDynamic ->
                val code = errorDynamic.asDynamic().code
                val message = errorDynamic.asDynamic().message?.toString() ?: "Unknown geolocation error"
                val publicMessage =
                    when (code?.unsafeCast<Int>()) {
                        1 -> "Location permission denied."
                        2 -> "Location position unavailable."
                        3 -> "Location request timed out."
                        else -> "Could not get location."
                    }
                logger.e("Geolocation error. Code: $code, Message: $message. Public: $publicMessage")
                weatherDataResult = Result.failure(Exception(publicMessage))
                isLoadingWeather = false
            },
            options = options,
        )
    }

    private fun fetchWeatherForCoords(
        lat: Double,
        lon: Double,
    ) {
        val apiKey = "3c1f971c84874bd78de20739250406"
        val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$lat,$lon&aqi=no"
        logger.d("Fetching weather from: $url")
        logger.d("Preparing to launch coroutine for API call...")

        GlobalScope.launch {
            try {
                logger.d("Coroutine launched. About to call window.fetch.")
                val response = window.fetch(url).await()
                logger.d("API Response status: ${response.status} (${response.statusText})")

                if (!response.ok) {
                    val errorBody = response.text().await()
                    val errorMsg = "API Error ${response.status} (${response.statusText}). Body: $errorBody"
                    logger.e("HTTP Error: $errorMsg")
                    throw Exception(errorMsg)
                }

                val responseText = response.text().await()
                logger.d("API Response text received (length: ${responseText.length}).")
                val weatherData =
                    parseWeatherData(responseText)
                        ?: throw Exception("Essential weather data fields missing or null")
                weatherDataResult = Result.success(weatherData)
            } catch (e: dynamic) {
                val errorMessage = e.message?.toString() ?: "Unknown error in fetch/parse chain"
                logger.e("Fetch/parse error: $errorMessage")
                weatherDataResult = Result.failure(Exception(errorMessage))
            }
            isLoadingWeather = false
        }
    }

    private fun parseWeatherData(responseText: String): WeatherData? {
        val parsedData: dynamic = JSON.parse(responseText)
        val current = parsedData?.current ?: return null

        val tempDynamic: Any? = current.temp_c
        val conditionObj: dynamic = current.condition
        val cloudDynamic: Any? = current.cloud

        val textFromConditionDynamic: Any? = conditionObj?.text
        val iconFromConditionDynamic: Any? = conditionObj?.icon

        if (tempDynamic == null || textFromConditionDynamic == null || iconFromConditionDynamic == null) {
            logger.e("Essential fields missing in 'current' object: $current")
            return null
        }

        val tempStr = tempDynamic.toString()
        val descriptionStr = textFromConditionDynamic.toString()
        var iconUrlStr = iconFromConditionDynamic.toString()
        if (iconUrlStr.startsWith("//")) iconUrlStr = "https:$iconUrlStr"

        val cloudInt: Int? = runCatching { cloudDynamic?.unsafeCast<Int>() }.getOrNull()

        logger.d("Parsed weather - Temp: $tempStr, Desc: $descriptionStr, Icon: $iconUrlStr, Cloud: $cloudInt")
        return WeatherData(tempStr, descriptionStr, iconUrlStr, cloudInt)
    }
}
