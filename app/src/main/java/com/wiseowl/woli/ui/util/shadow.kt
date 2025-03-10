package com.wiseowl.woli.ui.util

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePaint

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
