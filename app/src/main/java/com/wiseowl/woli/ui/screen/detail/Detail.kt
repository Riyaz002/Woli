package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    imageId: String
) {

    val detailUseCase: DetailUseCase by inject(DetailUseCase::class.java)
    val viewModel = viewModel{ DetailViewModel(imageId, detailUseCase)}
    val state = viewModel.state.collectAsState()
    Box {
        Column {
                state.value.let {
                    if(it is DetailState.Success){
                        it.detailModel.image?.let { it1 ->
                            ImageCard(
                                modifier = Modifier.fillMaxSize(),
                                image = it1,
                                onClick = viewModel::onEvent
                            )
                        }
                }
            }
        }
    }
}