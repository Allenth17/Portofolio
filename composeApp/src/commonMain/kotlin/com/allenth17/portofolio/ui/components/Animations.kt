package com.allenth17.portofolio.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import kotlinx.coroutines.delay

fun Modifier.hoverScale(
    scaleFactor: Float = 1.05f,
    animationDuration: Int = 200
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) scaleFactor else 1f,
        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing),
        label = "Hover Scale Anim"
    )

    this
        .hoverable(interactionSource = interactionSource)
        .pointerHoverIcon(PointerIcon.Hand)
        .scale(scale)
}

fun Modifier.floatingAnimation(
    durationMillis: Int = 3000,
    offsetY: Float = 10f
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "floating")
    val translation by infiniteTransition.animateFloat(
        initialValue = -offsetY,
        targetValue = offsetY,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating_offset"
    )

    this.graphicsLayer {
        translationY = translation
    }
}

@Composable
fun EntranceAnimation(
    delayMillis: Long = 100,
    content: @Composable () -> Unit
) {

    var hasAnimated by rememberSaveable { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(hasAnimated) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible || hasAnimated) 1f else 0f,
        animationSpec = tween(800, easing = EaseInOutSine),
        label = "Entrance Alpha"
    )

    val translationY by animateFloatAsState(
        targetValue = if (isVisible || hasAnimated) 0f else 100f,
        animationSpec = tween(800, easing = EaseInOutSine),
        label = "Entrance Translation"
    )

    LaunchedEffect(Unit) {
        if (!hasAnimated) {
            delay(delayMillis)
            isVisible = true
            hasAnimated = true
        }
    }

    androidx.compose.foundation.layout.Box(
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha
            this.translationY = translationY
        }
    ) {
        content()
    }
}
