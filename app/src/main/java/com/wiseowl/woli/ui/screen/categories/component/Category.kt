package com.wiseowl.woli.ui.screen.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.aspectRatio

@Composable
fun Category(
    modifier: Modifier = Modifier,
    category: com.wiseowl.woli.domain.model.Category,
    cornerRadius: Dp = 20.dp,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(category.cover.color!!.primary).copy(0.3f),
                        Color(category.cover.color.primary)
                    )
                )
            )
            .clip(RoundedCornerShape(cornerRadius))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ImageCard(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f).clip(
                    RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)
                ),
            image = category.cover,
            cornerRadius = 0.dp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = category.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}