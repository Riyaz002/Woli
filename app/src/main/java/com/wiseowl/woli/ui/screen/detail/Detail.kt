package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.configuration.Constant
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.detail.component.ChooserDialog
import com.wiseowl.woli.ui.screen.detail.component.ExpandableImageCard
import com.wiseowl.woli.ui.screen.detail.component.TextRoundButton
import com.wiseowl.woli.ui.screen.home.component.aspectRatio
import com.wiseowl.woli.ui.shared.component.Shimmer
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    imageId: String
) {
    val scrollState = rememberScrollState()
    val useCase: DetailUseCase by inject(DetailUseCase::class.java)
    val viewModel = viewModel{ DetailViewModel(imageId, useCase) }
    val state = viewModel.state.collectAsState()
    val detailState = if(state.value is Result.Success) state.value as Result.Success else null
    val complementaryColor = detailState?.data?.complementaryColor?.let { Color(it) } ?: MaterialTheme.colorScheme.background
    val accent = detailState?.data?.accentColor?.let { Color(it) } ?: MaterialTheme.colorScheme.background
    Screen(
        data = state.value
    ) { data ->
        Column(
            modifier
                .fillMaxSize()
                .background(accent)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .background(accent)
                    .padding(bottom = 20.dp)
            ) {
                data.media .let { image ->
                    if (image == null) {
                        Shimmer(
                            modifier = Modifier
                                .padding(top = 100.dp, start = Constant.DEFAULT_PADDING.dp, end = Constant.DEFAULT_PADDING.dp)
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    } else {
                        ExpandableImageCard(
                            modifier = Modifier
                                .padding(top = 100.dp, start = Constant.DEFAULT_PADDING.dp, end = Constant.DEFAULT_PADDING.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            media = image,
                            expanded = data.imagePreviewPopupVisible,
                            onDismiss = { viewModel.onEvent(DetailAction.OnDismissImagePreview) },
                            onClick = viewModel::onEvent
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceAround,
                ) {
                    TextRoundButton(
                        modifier = Modifier.weight(1f),
                        text = "Preview",
                        backgroundColor = complementaryColor,
                        textColor = accent,
                        onClick = { viewModel.onEvent(DetailAction.OnClickImage) }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextRoundButton(
                        modifier = Modifier.weight(1f),
                        text = "Set",
                        backgroundColor = complementaryColor,
                        textColor = accent,
                        onClick = { viewModel.onEvent(DetailAction.OnClickSetWallpaper) }
                    )
                }

                TextRoundButton(
                    modifier = Modifier.fillMaxWidth().padding(Constant.DEFAULT_PADDING.dp),
                    text = "Download",
                    backgroundColor = complementaryColor,
                    textColor = accent,
                    onClick = { viewModel.onEvent(DetailAction.OnClickDownload) }
                )

                data.description?.let { it1 ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        text = it1,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 42.sp,
                        fontWeight = FontWeight.Medium,
                        color = complementaryColor
                    )
                }
            }

            if (data.setWallpaperPopupVisible) {
                ChooserDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    buttonColor = Color(data.accentColor!!),
                    backgroundColor = complementaryColor,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}