package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.component.ChooserDialog
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
    val detailState = if(state.value is DetailState.Success) state.value as DetailState.Success else null
    val complementaryColor = detailState?.detailModel?.complementaryColor?.let { Color(it) } ?: MaterialTheme.colorScheme.onBackground
    Box(
        modifier.fillMaxSize().background(complementaryColor)
    ) {
        state.value.let {
            if(it is DetailState.Success){
                Column {
                    it.detailModel.image?.let { image ->
                        ExpandableImageCard(
                            modifier = Modifier
                                .padding(top = 100.dp, start = 20.dp, end = 20.dp),
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
                            backgroundColor = Color(it.detailModel.accentColor!!),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickImage) }
                        )
                        TextRoundButton(
                            text = "Set",
                            backgroundColor = Color(it.detailModel.accentColor),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickSetWallpaper)}
                        )
                    }
                }

                if(it.detailModel.setWallpaperPopupVisible){
                    ChooserDialog(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        buttonColor = Color(it.detailModel.accentColor!!),
                        backgroundColor = complementaryColor,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}