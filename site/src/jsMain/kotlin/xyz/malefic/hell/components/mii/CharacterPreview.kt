package xyz.malefic.hell.components.mii

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.hell.components.player.CharacterColors
import org.jetbrains.compose.web.css.Color as CssColor

@Composable
fun CharacterPreview(colors: CharacterColors) {
    Div(
        Modifier
            .width(200.px)
            .height(300.px)
            .backgroundColor(CssColor("#333"))
            .borderRadius(10.px)
            .padding(20.px)
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .toAttrs(),
    ) {
        Div(
            Modifier
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
                .alignItems(AlignItems.Center)
                .toAttrs(),
        ) {
            Div(
                Modifier
                    .size(60.px)
                    .borderRadius(50.percent)
                    .backgroundColor(CssColor(colors.head))
                    .toAttrs(),
            )

            Div(
                Modifier
                    .size(width = 80.px, height = 100.px)
                    .backgroundColor(CssColor(colors.body))
                    .borderRadius(5.px)
                    .margin(top = 5.px)
                    .toAttrs(),
            ) {
                Div(
                    Modifier
                        .display(DisplayStyle.Flex)
                        .justifyContent(JustifyContent.SpaceBetween)
                        .width(120.px)
                        .margin(leftRight = (-20).px, top = 15.px)
                        .toAttrs(),
                ) {
                    Div(
                        Modifier
                            .size(width = 20.px, height = 70.px)
                            .backgroundColor(CssColor(colors.arms))
                            .borderRadius(5.px)
                            .toAttrs(),
                    )
                    Div(
                        Modifier
                            .size(width = 20.px, height = 70.px)
                            .backgroundColor(CssColor(colors.arms))
                            .borderRadius(5.px)
                            .toAttrs(),
                    )
                }
            }

            Div(
                Modifier
                    .display(DisplayStyle.Flex)
                    .justifyContent(JustifyContent.SpaceBetween)
                    .width(60.px)
                    .toAttrs(),
            ) {
                Div(
                    Modifier
                        .size(width = 25.px, height = 80.px)
                        .backgroundColor(CssColor(colors.legs))
                        .borderRadius(5.px)
                        .toAttrs(),
                )
                Div(
                    Modifier
                        .size(width = 25.px, height = 80.px)
                        .backgroundColor(CssColor(colors.legs))
                        .borderRadius(5.px)
                        .toAttrs(),
                )
            }
        }
    }
}
