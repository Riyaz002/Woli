package com.wiseowl.woli.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import com.wiseowl.woli.ui.configuration.Constant
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.shared.component.BasicTextField
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    val useCase: MediaUseCase by inject(MediaUseCase::class.java)
    val viewModel: HomeViewModel =  viewModel{ HomeViewModel(useCase) }
    val state = viewModel.state.collectAsState()

    Screen(data = state.value){ data ->
        Box(modifier = modifier) {
            Column {
                BasicTextField(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = Constant.DEFAULT_PADDING.dp)
                        .fillMaxWidth(),
                    data = data.search,
                    onChange = { viewModel.onEvent(HomeAction.OnSearchChange(it)) },
                    trailingIcon = Icons.Default.Search,
                    onTrailingIconClick = { viewModel.onEvent(HomeAction.OnClickSearch) }
                )
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    items(data.images, key = { it.id }) { image ->
                        ImageCard(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            image = image,
                            cornerRadius = 20.dp,
                            aspectRatio = 0.6f,
                            onClick = { viewModel.onEvent(HomeAction.OnClickImage(image.id)) }
                        ){
                            Icon(
                                imageVector = if(data.favourites.contains(image.id)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                tint = if(data.favourites.contains(image.id)) Color.Green else Color.Red,
                                contentDescription = "add to favourites",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(20.dp)
                                    .size(20.dp)
                                    .clickable {
                                        viewModel.onEvent(HomeAction.OnClickAddToFavourite(image.id))
                                    },
                            )
                        }
                    }
                    items(
                        1,
                        span = { GridItemSpan(2) }
                    ){
                        viewModel.onEvent(HomeAction.LoadNextPage)
                        LoaderFooter(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

}