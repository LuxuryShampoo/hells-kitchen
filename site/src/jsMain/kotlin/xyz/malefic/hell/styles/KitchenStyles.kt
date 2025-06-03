package xyz.malefic.hell.styles

import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

object KitchenStyles {
    val container = WiiHomeStyles.container

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
                    .padding(top = 2.vh)
            }
        }

    val kitchenContainer =
        CssStyle {
            base {
                Modifier
                    .width(800.px)
                    .height(500.px)
                    .backgroundColor(Color.rgb(240, 240, 240))
                    .borderRadius(15.px)
                    .boxShadow(offsetY = 5.px, blurRadius = 15.px, color = rgba(0, 0, 0, 0.3))
                    .position(Position.Relative)
                    .overflow(Overflow.Hidden)
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
            }
        }

    val floor =
        CssStyle {
            base {
                Modifier
                    .fillMaxSize()
                    .backgroundColor(Color.rgb(220, 220, 220))
                    .styleModifier {
                        property(
                            "background-image",
                            "linear-gradient(to bottom, #e0e0e0 1px, transparent 1px), " +
                                "linear-gradient(to right, #e0e0e0 1px, #d8d8d8 1px)",
                        )
                        property("background-size", "40px 40px")
                    }
            }
        }

    val counter =
        CssStyle {
            base {
                Modifier
                    .width(200.px)
                    .height(100.px)
                    .backgroundColor(Color.rgb(150, 120, 90))
                    .position(Position.Absolute)
                    .left(100.px)
                    .top(100.px)
                    .borderRadius(8.px)
                    .boxShadow(offsetY = 3.px, blurRadius = 5.px, color = rgba(0, 0, 0, 0.2))
            }
        }

    val cuttingBoard =
        CssStyle {
            base {
                Modifier
                    .width(120.px)
                    .height(70.px)
                    .backgroundColor(Color.rgb(240, 230, 210))
                    .position(Position.Absolute)
                    .left(40.px)
                    .top(15.px)
                    .borderRadius(5.px)
            }
        }

    val stove =
        CssStyle {
            base {
                Modifier
                    .width(180.px)
                    .height(120.px)
                    .backgroundColor(Color.rgb(60, 60, 60))
                    .position(Position.Absolute)
                    .left(500.px)
                    .top(100.px)
                    .borderRadius(8.px)
                    .boxShadow(offsetY = 3.px, blurRadius = 5.px, color = rgba(0, 0, 0, 0.2))
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Row)
                    .flexWrap(FlexWrap.Wrap)
                    .alignItems(AlignItems.Center)
                    .justifyContent(JustifyContent.Center)
            }
        }

    val burner =
        CssStyle {
            base {
                Modifier
                    .width(40.px)
                    .height(40.px)
                    .backgroundColor(Color.rgb(80, 80, 80))
                    .borderRadius(50.percent)
                    .margin(10.px)
            }
        }

    val burnerTall =
        CssStyle {
            base {
                Modifier
                    .width(40.px)
                    .height(90.px)
                    .backgroundColor(Color.rgb(80, 80, 80))
                    .borderRadius(20.px)
                    .margin(10.px)
            }
        }

    val character =
        CssStyle {
            base {
                Modifier
                    .width(30.px)
                    .height(30.px)
                    .backgroundColor(Color.rgb(0, 102, 204))
                    .borderRadius(50.percent)
                    .position(Position.Absolute)
                    .zIndex(10)
                    .boxShadow(offsetY = 2.px, blurRadius = 4.px, color = rgba(0, 0, 0, 0.2))
                    .transition(Transition.of(property = "left", duration = 100.ms))
                    .transition(Transition.of(property = "top", duration = 100.ms))
            }
        }

    val characterFace =
        CssStyle {
            base {
                Modifier
                    .width(20.px)
                    .height(10.px)
                    .position(Position.Absolute)
                    .left(5.px)
                    .top(10.px)
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
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Row)
                    .justifyContent(JustifyContent.SpaceBetween)
                    .alignItems(AlignItems.Center)
                    .padding(leftRight = 5.vw)
            }
        }

    val instructions =
        CssStyle {
            base {
                Modifier
                    .fontSize(18.px)
                    .color(Color.rgb(80, 80, 80))
                    .fontWeight(700)
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
