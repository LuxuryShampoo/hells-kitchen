package xyz.malefic.hell.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.border
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
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh

object WiiChannelStyles {
    val container =
        CssStyle {
            base {
                Modifier
                    .fillMaxSize()
                    .fontFamily("Continuum", "Arial", "sans-serif")
                    .position(Position.Fixed)
                    .top(0.px)
                    .left(0.px)
                    .right(0.px)
                    .bottom(0.px)
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
            }
        }

    val background =
        CssStyle {
            base {
                Modifier
                    .fillMaxSize()
                    .backgroundImage(
                        linearGradient(
                            Color.rgb(135, 206, 235),
                            Color.rgb(255, 255, 255),
                            LinearGradient.Direction.ToBottom,
                        ),
                    ).position(Position.Relative)
            }
        }

    val content =
        CssStyle {
            base {
                Modifier
                    .fillMaxWidth()
                    .height(85.vh)
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .position(Position.Relative)
            }
        }

    val headerSection =
        CssStyle {
            base {
                Modifier
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .margin(bottom = 40.px)
            }
        }

    val iconContainer =
        CssStyle {
            base {
                Modifier
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .margin(bottom = 20.px)
                    .position(Position.Relative)
            }
        }

    val shoppingBag =
        CssStyle {
            base {
                Modifier
                    .width(120.px)
                    .height(140.px)
                    .backgroundColor(Color.rgb(0, 174, 239)) // Bright blue
                    .borderRadius(15.px, 15.px, 25.px, 25.px)
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .color(Color.rgb(255, 255, 255))
                    .fontSize(24.px)
                    .fontWeight(700)
                    .position(Position.Relative)
                    .zIndex(2)
                    .boxShadow(offsetX = 3.px, offsetY = 3.px, blurRadius = 10.px, color = rgba(0, 0, 0, 0.3))
            }
        }

    val shoppingBagSecondary =
        CssStyle {
            base {
                Modifier
                    .width(120.px)
                    .height(140.px)
                    .backgroundColor(Color.rgb(180, 120, 255)) // Purple/pink
                    .borderRadius(15.px, 15.px, 25.px, 25.px)
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .color(Color.rgb(255, 255, 255))
                    .fontSize(20.px)
                    .fontWeight(700)
                    .position(Position.Absolute)
                    .left(60.px)
                    .zIndex(1)
                    .boxShadow(offsetX = 3.px, offsetY = 3.px, blurRadius = 10.px, color = rgba(0, 0, 0, 0.3))
            }
        }

    val title =
        CssStyle {
            base {
                Modifier
                    .fontSize(48.px)
                    .fontWeight(700)
                    .color(Color.rgb(0, 150, 220))
                    .textAlign(TextAlign.Center)
                    .margin(topBottom = 20.px)
            }
        }

    val leftArrow =
        CssStyle {
            base {
                Modifier
                    .position(Position.Absolute)
                    .left(50.px)
                    .top(50.percent)
                    .transform { translateY((-50).percent) }
                    .fontSize(60.px)
                    .color(Color.rgb(200, 200, 200))
                    .cursor(Cursor.Pointer)
                    .transition(Transition.of(property = "color", duration = 200.ms))
            }

            hover {
                Modifier.color(Color.rgb(0, 150, 220))
            }
        }

    val rightArrow =
        CssStyle {
            base {
                Modifier
                    .position(Position.Absolute)
                    .right(50.px)
                    .top(50.percent)
                    .transform { translateY((-50).percent) }
                    .fontSize(60.px)
                    .color(Color.rgb(200, 200, 200))
                    .cursor(Cursor.Pointer)
                    .transition(Transition.of(property = "color", duration = 200.ms))
            }

            hover {
                Modifier.color(Color.rgb(0, 150, 220))
            }
        }

    val bottomBar =
        CssStyle {
            base {
                Modifier
                    .fillMaxWidth()
                    .height(15.vh)
                    .backgroundColor(Color.rgb(240, 240, 240))
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
                    .borderRadius(topLeft = 30.px, topRight = 30.px)
                    .boxShadow(offsetY = (-2).px, blurRadius = 10.px, color = rgba(0, 0, 0, 0.1))
                    .position(Position.Absolute)
                    .bottom(0.px)
                    .left(0.px)
                    .right(0.px)
            }
        }

    val navButton =
        CssStyle {
            base {
                Modifier
                    .width(180.px)
                    .height(50.px)
                    .margin(leftRight = 20.px)
                    .backgroundColor(Color.rgb(255, 255, 255))
                    .border(3.px, LineStyle.Solid, Color.rgb(0, 150, 220))
                    .borderRadius(25.px)
                    .fontSize(18.px)
                    .fontWeight(600)
                    .color(Color.rgb(0, 150, 220))
                    .cursor(Cursor.Pointer)
                    .transition(Transition.of(property = "all", duration = 200.ms))
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
            }

            hover {
                Modifier
                    .backgroundColor(Color.rgb(0, 150, 220))
                    .color(Color.rgb(255, 255, 255))
                    .transform { scale(1.05) }
            }
        }
}
