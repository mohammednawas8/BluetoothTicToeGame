package com.example.bluetoothtictoegame.screens.client_screen.components


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCircleLoading(
    circleSize: Float
) {
    val infiniteTransition = rememberInfiniteTransition()
    val colorAnimation = infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.secondary,
        targetValue = Color.Transparent,
        animationSpec = InfiniteRepeatableSpec(
            tween(1200, easing = LinearEasing),
        )
    ).value
    val sizeAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = circleSize,
        animationSpec = InfiniteRepeatableSpec(
            tween(1200, easing = LinearEasing)
        )
    ).value


    Circle(size = Size(sizeAnimation, sizeAnimation), color = colorAnimation)

}

@Composable
fun Circle(
    size: Size,
    color: Color
) {

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(size.width.dp, size.height.dp)
            .background(color)
    )

}