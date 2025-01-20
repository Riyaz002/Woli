package com.wiseowl.woli.ui.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.ui.screen.home.HomeEvent
import kotlin.reflect.KFunction1

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Image,
    onClick: KFunction1<Event, Unit>
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(HomeEvent.OnClickImage(image.id)) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .build(),
            contentDescription = image.description,
            contentScale = ContentScale.Crop
        )
    }
}