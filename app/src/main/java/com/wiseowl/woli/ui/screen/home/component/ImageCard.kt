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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.media.model.Photo

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Image,
    cornerRadius: Dp = 20.dp,
    aspectRatio: Float? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.clickable { onClick?.invoke() }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .aspectRatio(aspectRatio),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .build(),
            contentDescription = image.description,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Photo,
    cornerRadius: Dp = 20.dp,
    aspectRatio: Float? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.clickable { onClick?.invoke() }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .aspectRatio(aspectRatio),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.src.original)
                .crossfade(true)
                .build(),
            contentDescription = image.alt,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Bitmap,
    description: String? = null,
    cornerRadius: Dp = 0.dp,
    aspectRatio: Float? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.clickable { onClick?.invoke() }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .aspectRatio(aspectRatio),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = description,
            contentScale = ContentScale.Crop
        )
    }
}

fun Modifier.aspectRatio(aspectRatio: Float?): Modifier{
    return if (aspectRatio != null) {
        this.then(Modifier.aspectRatio(aspectRatio))
    } else this
}