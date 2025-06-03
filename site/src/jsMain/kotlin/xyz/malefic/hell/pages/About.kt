package xyz.malefic.hell.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.Text

@Composable
@Page("/about")
fun AboutPage() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Column(Modifier.fillMaxSize(), Arrangement.SpaceAround, Alignment.CenterHorizontally) {
            Spacer()
            Text("THIS TEMPLATE WAS CREATED BY MALEFIC")
            Spacer()
            Text("YOU SHOULD PROBABLY REPLACE THIS PAGE")
            Spacer()
            Text("JUST MAYBE")
            Spacer()
        }
    }
}
