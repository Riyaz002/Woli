package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.component.ExpandableImageCard
import com.wiseowl.woli.ui.screen.detail.component.TextRoundButton
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    imageId: String
) {

    val detailUseCase: DetailUseCase by inject(DetailUseCase::class.java)
    val viewModel = viewModel{ DetailViewModel(imageId, detailUseCase) }
    val state = viewModel.state.collectAsState()
    Box(
        modifier
            .fillMaxSize()

    ) {
        state.value.let {
            if(it is DetailState.Success){
                Column {
                    it.detailModel.image?.let { image ->
                        ExpandableImageCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp, horizontal = 20.dp),
                            image = image,
                            expanded = it.detailModel.imagePreviewPopupVisible,
                            onDismiss = { viewModel.onEvent(DetailEvent.OnDismissImagePreview) },
                            onClick = viewModel::onEvent
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceAround
                    ) {
                        TextRoundButton(
                            text = "Preview",
                            onClick = { viewModel.onEvent(DetailEvent.OnClickImage) }
                        )
                        TextRoundButton(
                            text = "Set",
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}