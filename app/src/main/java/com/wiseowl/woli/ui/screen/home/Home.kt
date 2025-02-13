package com.wiseowl.woli.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import com.wiseowl.woli.ui.screen.home.model.HomeState
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    val useCase: HomeUseCase by inject(HomeUseCase::class.java)
    val viewModel: HomeViewModel =  viewModel{ HomeViewModel(useCase) }
    val state = viewModel.state.collectAsState()


    Box(modifier = modifier) {
        when(val data = state.value){
            HomeState.Loading -> Unit
            is HomeState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    data.homePageModel.images?.let { images ->
                        items(images.size){ index ->
                            ImageCard(
                                modifier = Modifier.padding(10.dp),
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
                            if(data.homePageModel.currentPage > 0){
                                viewModel.onEvent(HomeEvent.LoadNextPage(data.homePageModel.currentPage.minus(1)))
                                LoaderFooter(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }
            is HomeState.Error -> Unit
        }
    }
}