package com.wiseowl.woli.ui.screen.categories.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wiseowl.woli.ui.screen.home.component.aspectRatio

@Composable
fun Category(
    modifier: Modifier = Modifier,
    category: com.wiseowl.woli.domain.model.Category,
    cornerRadius: Dp = 20.dp
) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(cornerRadius))
    ) {
        Text(text = category.name, fontSize = 22.sp)
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .aspectRatio(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.cover)
                .crossfade(true)
                .build(),
            contentDescription = category.name,
            contentScale = ContentScale.Crop
        )
    }
}