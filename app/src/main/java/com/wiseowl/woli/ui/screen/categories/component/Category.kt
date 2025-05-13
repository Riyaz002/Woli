package com.wiseowl.woli.ui.screen.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.aspectRatio

@Preview
@Composable
fun Category(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    images: List<Media>,
    cornerRadius: Dp = 20.dp,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .background(Color.Gray)
            .clip(RoundedCornerShape(cornerRadius))
            .padding(vertical = 20.dp, horizontal = 16.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        LazyRow {
            items(images) {
                ImageCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(1f)
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(20.dp)),
                    image = it,
                    cornerRadius = 20.dp,
                    aspectRatio = 0.6f,
//                    onClick = { viewModel.onEvent(HomeEvent.OnClickImage(image.id)) }
                )
            }
        }

    }
}