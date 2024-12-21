package com.example.patasfelizes.ui.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

@Composable
fun ToggleSwitch(
    options: List<String>,
    selectedOptionIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 22.dp,
    verticalPadding: Dp = 8.dp,
    animationDuration: Int = 300
) {
    var containerWidth by remember { mutableStateOf(0.dp) }
    val optionWidth = if (options.isNotEmpty()) containerWidth / options.size else 0.dp

    val coroutineScope = rememberCoroutineScope()
    val indicatorOffset = remember { Animatable(0f) }

    val density = LocalDensity.current
    LaunchedEffect(selectedOptionIndex, containerWidth) {
        val target = with(density) { optionWidth.toPx() * selectedOptionIndex }
        indicatorOffset.animateTo(
            targetValue = target,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(30)
            )
            .onGloballyPositioned { layoutCoordinates ->
                containerWidth = with(density) { layoutCoordinates.size.width.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(indicatorOffset.value.toInt(), 0) }
                .width(optionWidth)
                .height(50.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(30)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clickable { onOptionSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = if (index == selectedOptionIndex) MaterialTheme.colorScheme.onPrimary else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

