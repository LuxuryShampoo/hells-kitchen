package xyz.malefic.hell.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.styles.WiiHomeStyles

private data class WeatherData(
    val temperature: String,
    val description: String,
    val iconUrl: String,
)

@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun LaunchedEffect(
    key: Any,
    block: suspend () -> Unit,
) {
    DisposableEffect(key) {
        val job =
            GlobalScope.launch {
                block()
            }
        onDispose { job.cancel() }
    }
}

private fun fetchWeather(callback: (Result<WeatherData>) -> Unit) {
    console.log("[fetchWeather] Called - Using WeatherAPI.com")
    val navigatorDynamic = window.navigator.asDynamic()

    if (js(
            "typeof navigatorDynamic.geolocation === 'undefined' || typeof navigatorDynamic.geolocation.getCurrentPosition !== 'function'",
        )
    ) {
        console.error("[fetchWeather] Geolocation API is not available.")
        callback(Result.failure(Exception("Geolocation API not available")))
        return
    }

    val geolocation = navigatorDynamic.geolocation
    console.log("[fetchWeather] Attempting to get current position (timeout: 10s, low accuracy)...")

    // Standard options object for geolocation
    val options = js("{ enableHighAccuracy: false, timeout: 10000, maximumAge: 600000 }")

    geolocation.getCurrentPosition(
        successCallback = { positionDynamic ->
            // Arrow function syntax
            console.log("[fetchWeather] Geolocation success. Position:", positionDynamic)
            val coords = positionDynamic.coords
            if (coords == null || js("typeof coords.latitude === 'undefined' || typeof coords.longitude === 'undefined'")) {
                console.error("[fetchWeather] Geolocation coordinates are missing or invalid.")
                callback(Result.failure(Exception("Geolocation coordinates missing")))
                return@getCurrentPosition // Explicitly return from lambda
            }

            val lat = coords.latitude
            val lon = coords.longitude
            console.log("[fetchWeather] Lat: $lat, Lon: $lon")
            val apiKey = "3c1f971c84874bd78de20739250406"
            val qParam = "$lat,$lon"
            // Ensure API is HTTPS if your site is HTTPS, or handle mixed content
            val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$qParam&aqi=no"
            console.log("[fetchWeather] Fetching weather from: $url")

            window
                .fetch(url)
                .then(
                    onFulfilled = { response ->
                        // Handles the response object from fetch
                        console.log("[fetchWeather] API Response status: ${response.status} (${response.statusText})")
                        if (!response.ok) {
                            // If response is not OK, get text body and throw an error to reject the promise chain
                            return@then response
                                .text()
                                .then { errorBody: String ->
                                    val errorMsg = "API Error ${response.status} (${response.statusText}). Body: $errorBody"
                                    console.error("[fetchWeather] HTTP Error: $errorMsg")
                                    throw Exception(errorMsg) // Throw to be caught by the final .catch
                                }.catch { texterror ->
                                    // Catch if .text() itself fails
                                    val errorMsg = "API Error ${response.status} (${response.statusText}). Failed to read error body: ${texterror.asDynamic().message}"
                                    console.error("[fetchWeather] HTTP Error: $errorMsg")
                                    throw Exception(errorMsg) // Throw to be caught by the final .catch
                                }
                        }
                        // If response is OK, return the promise for the response text
                        response.text()
                    },
                    onRejected = { networkError ->
                        // Handles failure of the fetch call itself (e.g. network down)
                        val errorMsg = "Network error fetching weather: ${networkError.asDynamic().message}"
                        console.error("[fetchWeather] $errorMsg", networkError)
                        throw Exception(errorMsg) // Rethrow to be caught by the final .catch
                    },
                ).then { responseText ->
                    // This .then is only called if fetch succeeded AND response.ok was true AND response.text() succeeded
                    // responseText is String
                    console.log("[fetchWeather] API Response text received (length: ${responseText.length}):\n'$responseText'")

                    val parsedData: Any? =
                        try {
                            if (responseText.isBlank()) {
                                console.warn("[fetchWeather] API response text is blank. Treating as empty object.")
                                kotlin.js.json() // Creates an empty JS object {}
                            } else {
                                JSON.parse(responseText)
                            }
                        } catch (e: dynamic) {
                            console.error("[fetchWeather] JSON parsing error: ${e.message}. Raw text was:\n'$responseText'")
                            throw Exception("Failed to parse weather JSON: ${e.message}")
                        }

                    console.log("[fetchWeather] Weather API JSON data successfully parsed (manual):", parsedData)

                    val current = parsedData?.asDynamic()?.current // Added null-check on parsedData before asDynamic()
                    console.log("[fetchWeather] Extracted 'current' object (from manual parse):", current)

                    if (current != null) {
                        try {
                            console.log("[fetchWeather] Attempting to log properties of 'current'...")
                            val currentDynamic = current.unsafeCast<dynamic>() // Explicit cast to dynamic

                            console.log("[fetchWeather] current.temp_c:", currentDynamic.temp_c)
                            val condition = currentDynamic.condition
                            console.log("[fetchWeather] current.condition:", condition)

                            if (condition != null) {
                                val conditionDynamic = condition.unsafeCast<dynamic>() // Explicit cast to dynamic
                                console.log("[fetchWeather] current.condition.text:", conditionDynamic.text)
                                console.log("[fetchWeather] current.condition.icon:", conditionDynamic.icon)
                            } else {
                                console.log(
                                    "[fetchWeather] current.condition is null, not an object, or undefined (checked before accessing text/icon).",
                                )
                            }
                        } catch (e: dynamic) {
                            console.error("[fetchWeather] Error accessing/logging properties of 'current' object: ${e.message}", e)
                        }
                    } else {
                        console.log(
                            "[fetchWeather] (Manual Parse) 'current' field is null or undefined in parsed data. Full parsed data:",
                            parsedData,
                        )
                    }

                    // Main check
                    if (current != null) {
                        val currentDynamic = current.unsafeCast<dynamic>()
                        val condition = currentDynamic.condition

                        if (condition != null) {
                            val conditionDynamic = condition.unsafeCast<dynamic>()
                            if (currentDynamic.temp_c != null &&
                                conditionDynamic.text != null &&
                                conditionDynamic.icon != null
                            ) {
                                // All checks passed, proceed to create WeatherData
                                val temp = currentDynamic.temp_c.toString()
                                val description = conditionDynamic.text.toString()
                                var iconUrl = conditionDynamic.icon.toString()
                                if (iconUrl.startsWith("//")) {
                                    iconUrl = "https:$iconUrl"
                                }
                                console.log("[fetchWeather] (Safe Cast) Parsed weather - Temp: $temp, Desc: $description, Icon: $iconUrl")
                                callback(Result.success(WeatherData(temp, description, iconUrl)))
                            } else {
                                // One of the nested properties (temp_c, text, icon) is null
                                console.error(
                                    "[fetchWeather] (Safe Cast) Weather data fields (temp_c, text, or icon) are null. Full parsed data:",
                                    parsedData,
                                )
                                callback(Result.failure(Exception("Weather data fields are null (safe cast)")))
                            }
                        } else {
                            // current.condition is null
                            console.error("[fetchWeather] (Safe Cast) current.condition is null. Full parsed data:", parsedData)
                            callback(Result.failure(Exception("Weather condition data is null (safe cast)")))
                        }
                    } else {
                        // current is null
                        console.error(
                            "[fetchWeather] (Safe Cast) Weather data from API is marked as incomplete after parsing (current is null). Full parsed data:",
                            parsedData,
                        )
                        callback(Result.failure(Exception("Weather data is incomplete (current is null, safe cast)")))
                    }
                }.catch { error ->
                    // Unified catch for network errors, HTTP errors, text errors, JSON parse errors
                    val errorMessage = error.asDynamic().message?.toString() ?: "Unknown error in fetch/parse chain"
                    // Error is logged at source, so just pass to callback
                    callback(Result.failure(Exception(errorMessage)))
                }
        },
        errorCallback = { errorDynamic ->
            // Arrow function syntax
            val code = errorDynamic.asDynamic().code
            var message = errorDynamic.asDynamic().message?.toString() ?: "Unknown geolocation error"
            var publicMessage = "Could not get location"

            when (code?.unsafeCast<Int>()) {
                1 -> publicMessage = "Location permission denied."
                2 -> publicMessage = "Location position unavailable."
                3 -> publicMessage = "Location request timed out."
            }
            console.error("[fetchWeather] Geolocation error. Code: $code, Message: $message. Public: $publicMessage", errorDynamic)
            callback(Result.failure(Exception(publicMessage)))
        },
        options = options, // Pass options object
    )
}

@Composable
fun FooterBar(
    time: String,
    date: String,
) {
    var weatherDataResult by remember { mutableStateOf<Result<WeatherData>?>(null) }
    var isLoadingWeather by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        console.log("[FooterBar] LaunchedEffect called. Setting isLoadingWeather = true.")
        isLoadingWeather = true
        fetchWeather { result ->
            console.log("[FooterBar] fetchWeather callback executed. Result: ", result)
            weatherDataResult = result
            isLoadingWeather = false
            console.log("[FooterBar] isLoadingWeather set to false.")
        }
    }

    Box(
        modifier =
            WiiHomeStyles.footer
                .toModifier()
                .fillMaxWidth()
                .padding(leftRight = 10.px, topBottom = 5.px),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                P(
                    attrs =
                        WiiHomeStyles.clock
                            .toModifier()
                            .color(Colors.White)
                            .fontSize(32.px)
                            .fontWeight(FontWeight.Bold)
                            .toAttrs(),
                ) {
                    Text(time)
                }
                P(
                    attrs =
                        WiiHomeStyles.date
                            .toModifier()
                            .color(Colors.White)
                            .fontSize(18.px)
                            .toAttrs(),
                ) {
                    Text(date)
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.margin(right = 20.px)
            ) {
                when {
                    isLoadingWeather -> {
                        P(attrs = {
                            style {
                                color(org.jetbrains.compose.web.css.Color.white)
                                fontSize(16.px)
                                margin(0.px)
                            }
                        }) {
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
                                    attrs =
                                        Modifier
                                            .color(Colors.White)
                                            .fontSize(30.px)
                                            .fontWeight(FontWeight.Bold)
                                            .margin(0.px)
                                            .toAttrs(),
                                ) {
                                    Text("${data.temperature}°C")
                                }
                                P(
                                    attrs =
                                        Modifier
                                            .color(Colors.White)
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
                            attrs =
                                Modifier
                                    .color(Color.rgb(0xF0, 0x80, 0x80))
                                    .fontSize(16.px)
                                    .margin(0.px)
                                    .toAttrs(),
                        ) {
                            Text("Weather unavailable")
                        }
                        console.error("[FooterBar] Weather fetch failed: ${weatherDataResult?.exceptionOrNull()?.message}")
                    }
                }
            }
        }
    }
}
