package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.component.ImageFullPreview
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
    Box(
        modifier.fillMaxSize()
    ) {
        Column {
                state.value.let {
                    if(it is DetailState.Success){
                        it.detailModel.image?.let { it1 ->
                            ImageCard(
                                modifier = Modifier.fillMaxWidth(),
                                image = it1,
                                cornerRadius = 20.dp,
                                aspectRatio = 1f,
                                onClick = { viewModel.onEvent(DetailEvent.OnClickImage) }
                            )
                        }

                        if (it.detailModel.imagePreviewPopupVisible) {
                            ImageFullPreview(
                                modifier = Modifier.fillMaxWidth(),
                                image = it.detailModel.image!!,
                                onDismiss = { viewModel.onEvent(DetailEvent.OnDismissImagePreview) }
                            )
                        }
                }
            }
        }
    }
}