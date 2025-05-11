package com.wiseowl.woli.ui.shared.component.modifier

import android.graphics.BlurMaskFilter
import android.graphics.RectF
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.ui.shared.Constant

@Composable
fun Modifier.glowingBorder(
    thickness: Int,
    color: Color,
    isGlowing: Boolean
): Modifier {
    var size by remember { mutableStateOf<IntSize?>(null) }
    val focused by rememberUpdatedState(isGlowing)
    val animateGlow = animateFloatAsState(
        targetValue = if (focused) thickness.toFloat() else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 350,
            easing = LinearEasing
        ),
    )

    return this
        .onSizeChanged {
            size = it
        }
        .drawBehind {
            size?.let { size ->
                drawContext.canvas.nativeCanvas.apply {
                    val paint = Paint().apply {
                        this.isAntiAlias = true
                        this.color = color
                        this.style = PaintingStyle.Stroke
                    }.asFrameworkPaint()

                    if(focused) {
                        drawRoundRect(
                            RectF(0f,0f, size.width.toFloat(), size.height.toFloat()),
                            Constant.DEFAULT_CORNER_RADIUS.dp.toPx() .toFloat(),
                            Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat(),
                            paint.apply {
                                this.strokeWidth = animateGlow.value * 3
                                this.maskFilter = BlurMaskFilter(thickness.toFloat()*3, BlurMaskFilter.Blur.NORMAL)
                            }
                        )

                        drawRoundRect(
                            RectF(0f,0f, size.width.toFloat(), size.height.toFloat()),
                            Constant.DEFAULT_CORNER_RADIUS.dp.toPx() .toFloat(),
                            Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat(),
                            paint.apply {
                                this.strokeWidth = animateGlow.value *2
                                this.maskFilter = BlurMaskFilter(thickness.toFloat(), BlurMaskFilter.Blur.NORMAL)
                            }
                        )
                    }

                    drawRoundRect(
                        RectF(-0.5f, -0.5f, size.width.toFloat()+0.5f, size.height.toFloat()+0.5f),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx() .toFloat()+0.5f,
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat()+0.5f,
                        paint.apply {
                            this.color = android.graphics.Color.BLACK
                            this.strokeWidth = thickness.toFloat()
                            this.maskFilter = null
                        }
                    )

                    drawRoundRect(
                        RectF(0.5f, 0.5f, size.width.toFloat()-0.5f, size.height.toFloat()-0.5f),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat()+0.5f,
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat()+0.5f,
                        paint.apply {
                            this.color = android.graphics.Color.BLACK
                            this.strokeWidth = thickness.toFloat()
                            this.maskFilter = null
                        }
                    )

                    drawRoundRect(
                        RectF(0f,0f, size.width.toFloat(), size.height.toFloat()),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx() .toFloat(),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat(),
                        paint.apply {
                            this.color = android.graphics.Color.WHITE
                            this.strokeWidth = thickness.toFloat()
                            this.maskFilter = null
                        }
                    )
                    drawRoundRect(
                        RectF(1f, 1f, size.width.toFloat(), size.height.toFloat()),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx() .toFloat(),
                        Constant.DEFAULT_CORNER_RADIUS.dp.toPx().toFloat(),
                        paint.apply {
                            this.color = android.graphics.Color.WHITE
                            this.strokeWidth = thickness.toFloat()
                            this.maskFilter = null
                        }
                    )
                }
            }
        }
}