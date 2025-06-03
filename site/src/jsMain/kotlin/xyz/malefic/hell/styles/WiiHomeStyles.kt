package xyz.malefic.hell.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.css.Color as CssColor

object WiiHomeStyles {
    val container =
        CssStyle {
            base {
                Modifier
                    .fillMaxSize()
                    .backgroundImage(linearGradient(CssColor("#d9ecff"), CssColor("#c7e1ff")))
                    .fontFamily("Continuum")
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
                    .position(Position.Fixed)
                    .top(0.px)
                    .left(0.px)
                    .right(0.px)
                    .bottom(0.px)
            }
        }

    val content =
        CssStyle {
            base {
                Modifier
                    .fillMaxWidth()
                    .height(85.vh)
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Row)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .padding(top = 2.vh)
            }
        }

    val sidebarLeft =
        CssStyle {
            base {
                Modifier
                    .width(15.vw)
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
            }
        }

    val sidebarRight =
        CssStyle {
            base {
                Modifier
                    .width(15.vw)
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
            }
        }

    val channelGrid =
        CssStyle {
            base {
                Modifier
                    .width(70.vw)
                    .display(DisplayStyle.Flex)
                    .justifyContent(JustifyContent.Center)
            }
        }

    val footer =
        CssStyle {
            base {
                Modifier
                    .fillMaxWidth()
                    .height(15.vh)
                    .backgroundColor(Color.rgb(220, 230, 240))
                    .borderRadius(topLeft = 30.px, topRight = 30.px)
                    .boxShadow(offsetY = (-2).px, blurRadius = 5.px, color = rgba(0, 0, 0, 0.1))
            }
        }

    val channelButton =
        CssStyle {
            base {
                Modifier
                    .width(12.vw)
                    .height(8.vh)
                    .minWidth(100.px)
                    .minHeight(70.px)
                    .maxWidth(150.px)
                    .maxHeight(100.px)
                    .borderRadius(10.px)
                    .margin(8.px)
                    .boxShadow(offsetY = 3.px, blurRadius = 5.px, color = rgba(0, 0, 0, 0.2))
                    .cursor(Cursor.Pointer)
                    .transition(Transition.of(property = "transform", duration = 200.ms))
            }

            hover {
                Modifier
                    .transform { scale(1.05) }
            }
        }

    val wiiButton =
        CssStyle {
            base {
                Modifier
                    .width(10.vw)
                    .height(10.vw)
                    .minWidth(80.px)
                    .minHeight(80.px)
                    .maxWidth(120.px)
                    .maxHeight(120.px)
                    .borderRadius(50.percent)
                    .backgroundColor(Color.rgb(0, 102, 204))
                    .boxShadow(offsetY = 3.px, blurRadius = 5.px, color = rgba(0, 0, 0, 0.2))
                    .cursor(Cursor.Pointer)
            }
        }

    val clock =
        CssStyle {
            base {
                Modifier
                    .fontSize(32.px)
                    .fontWeight(700)
                    .color(Color.rgb(80, 80, 80))
                    .margin(topBottom = 0.px)
            }
        }

    val date =
        CssStyle {
            base {
                Modifier
                    .fontSize(18.px)
                    .color(Color.rgb(100, 100, 100))
                    .margin(topBottom = 0.px)
            }
        }
}
