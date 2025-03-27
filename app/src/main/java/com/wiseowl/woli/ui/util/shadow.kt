package com.wiseowl.woli.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    offsetX: Float = 2f,
    offsetY: Float = 2f,
    blurRadius: Float = 10f,
    shadowColor: Color = Color.Black,
    shape: Shape = RectangleShape
) = this
    .background(Color.Transparent, shape)
    .drawBehind {
        val paint = Paint().apply {
            color = shadowColor.copy(alpha = 0.5f)
            style = PaintingStyle.Fill
        }.asFrameworkPaint().apply {
            maskFilter = android.graphics.BlurMaskFilter(
                blurRadius, // Blur radius
                android.graphics.BlurMaskFilter.Blur.NORMAL
            )
        }

        val leftOffset = offsetX
        val rightOffset = size.width + offsetX
        val topOffset = offsetY
        val bottomOffset = size.height + offsetY

        val rect = Rect(-leftOffset, -topOffset, rightOffset, bottomOffset)
        drawContext.canvas.drawRect(
            rect,
            paint.asComposePaint()
        )
}

fun Modifier.neumorphism(
    strength: Float = 30f,
    blurRadius: Dp = 20.dp,
    cornerRadius: Dp = 0.dp
) = this
    .drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint().asFrameworkPaint().apply {
                shader = LinearGradientShader(
                    from = Offset.Zero,
                    to = Offset(size.width, size.height),
                    colors = listOf(Color.White, Color.Black.copy(0.3f))
                )
                maskFilter = android.graphics.BlurMaskFilter(
                    blurRadius.toPx(),  // Blur Radius
                    android.graphics.BlurMaskFilter.Blur.NORMAL
                )
            }
            canvas.drawRoundRect(
                -strength,
                -strength,
                size.width+strength,
                size.height+strength,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                paint.asComposePaint()
            )
        }
    }.clip(RoundedCornerShape(cornerRadius))
