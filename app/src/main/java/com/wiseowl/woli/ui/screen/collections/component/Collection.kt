package com.wiseowl.woli.ui.screen.collections.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.repository.media.model.Collection
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.aspectRatio
import com.wiseowl.woli.ui.configuration.Constant
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.navigation.Screen.COLLECTION
import com.wiseowl.woli.ui.screen.collections.CollectionsAction
import com.wiseowl.woli.ui.shared.component.Shimmer

@Preview
@Composable
fun Collection(
    modifier: Modifier = Modifier.fillMaxWidth(),
    collection: Collection,
    onAction: (Action) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .background(Color.Gray)
            .clip(RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS))
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = collection.title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text(
                modifier = Modifier.clickable{
                    onAction(Action.Navigate(COLLECTION, mapOf(
                        COLLECTION.ARG_COLLECTION_ID to collection.id,
                        COLLECTION.ARG_COLLECTION_TITLE to collection.title
                    )))
                },
                text = "View All",
                style = TextStyle(
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        LazyRow(Modifier.padding(top = 10.dp)) {
            if(collection.medias.isEmpty()){
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
                items(collection.medias, key = { it.id }) {
                    ImageCard(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .width(100.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        image = it,
                        cornerRadius = 20.dp,
                        aspectRatio = 0.6f,
                      onClick = { onAction(CollectionsAction.OnClickMedia(it.id)) }
                    )
                }
            }
        }

    }
}