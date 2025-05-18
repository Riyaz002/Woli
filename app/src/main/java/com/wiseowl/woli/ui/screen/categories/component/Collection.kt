package com.wiseowl.woli.ui.screen.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.aspectRatio
import com.wiseowl.woli.ui.shared.Constant
import com.wiseowl.woli.ui.shared.component.Shimmer

@Preview
@Composable
fun Collection(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    images: List<Media>,
    onClick: (id: Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .background(Color.Gray)
            .clip(RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS))
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        LazyRow(Modifier.padding(top = 10.dp)) {
            if(images.isEmpty()){
                items(5){
                    Shimmer(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .width(100.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            } else{
                items(images, key = { it.id }) {
                    ImageCard(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .width(100.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        image = it,
                        cornerRadius = 20.dp,
                        aspectRatio = 0.6f,
                      onClick = { onClick(it.id) }
                    )
                }
            }
        }

    }
}