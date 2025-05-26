package com.wiseowl.woli.ui.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.collections.CollectionsAction
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Collection(
    modifier: Modifier = Modifier,
    categoryId: String,
    categoryTitle: String
) {

    val viewModel = viewModel {
        val mediaUseCase: MediaUseCase by inject(MediaUseCase::class.java)
        CollectionViewModel(categoryId, categoryTitle, mediaUseCase)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        data = state.value
    ) { data ->
        Column(modifier) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.category,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = 10.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(data.media.orEmpty()){ media ->
                    ImageCard(
                        modifier = Modifier.padding(10.dp),
                        image = media,
                        aspectRatio = 0.6f,
                        onClick = {
                            viewModel.onEvent(
                                Action.Navigate(
                                    Screen.DETAIL,
                                    mapOf(Screen.DETAIL.ARG_IMAGE_ID to media.id.toString())
                                )
                            )
                        }
                    )
                }
                item{
                    if(data.hasNext){
                        viewModel.onEvent(CollectionsAction.LoadPage(data.currentPage.plus(1)))
                        LoaderFooter(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}