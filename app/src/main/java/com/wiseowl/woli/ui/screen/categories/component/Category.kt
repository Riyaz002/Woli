package com.wiseowl.woli.ui.screen.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Row(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(category.cover.color!!.primary).copy(0.3f),
                        Color(category.cover.color.primary)
                    )
                ), shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { onClick() }
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category.name, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
        ImageCard(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(vertical = 10.dp),
            image = category.cover
        )
    }
}