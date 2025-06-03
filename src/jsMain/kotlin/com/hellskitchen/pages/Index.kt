package com.hellskitchen.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    var miiClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.px),
    ) {
        Button(
            attrs = {
                onClick { miiClicked = true }
                style {
                    if (!miiClicked) {
                        border(6.px, LineStyle.Solid, Color("#FFD700"))
                        boxShadow(0.px, 0.px, 10.px, 2.px, Color("rgba(255, 215, 0, 0.7)"))
                        backgroundColor(Color.white)
                    } else {
                        border(1.px, LineStyle.Solid, Color.transparent)
                    }
                    padding(8.px, 16.px)
                    margin(10.px)
                    borderRadius(4.px)
                    transition(CSSTransition(property = "all", duration = 200.ms))
                }
                if (miiClicked) {
                    disabled()
                }
            },
        ) {
            Text("mii")
        }

        Button(
            attrs = {
                style {
                    if (!miiClicked) {
                        backgroundColor(Color("#ccc"))
                        color(Color("#888"))
                        cursor(Cursor.NotAllowed)
                    }
                    padding(8.px, 16.px)
                    margin(10.px)
                    borderRadius(4.px)
                    transition(CSSTransition(property = "background-color", duration = 200.ms))
                    transition(CSSTransition(property = "color", duration = 200.ms))
                }
                if (!miiClicked) {
                    disabled()
                }
            },
        ) {
            Text("level one")
        }
    }
}
