package com.wiseowl.woli.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    val useCase: HomeUseCase by inject(HomeUseCase::class.java)
    val viewModel: HomeViewModel =  viewModel{ HomeViewModel(useCase) }
    val state = viewModel.state.collectAsState()


    Box(modifier = modifier) {
        when(val currentState = state.value){
            is Result.Loading -> Unit
            is Result.Success<HomePageModel> -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    currentState.data.images?.let { images ->
                        items(images.size){ index ->
                            ImageCard(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(20.dp)),
                                image = images[index],
                                cornerRadius = 20.dp,
                                aspectRatio = 0.6f,
                                onClick = { viewModel.onEvent(HomeEvent.OnClickImage(images[index].id)) }
                            )
                        }
                        items(
                            1,
                            span = { GridItemSpan(2) }
                        ){
                            if(currentState.data.currentPage > 0){
                                viewModel.onEvent(HomeEvent.LoadNextPage(currentState.data.currentPage.minus(1)))
                                LoaderFooter(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }
            is Result.Error -> Unit
        }
    }
}