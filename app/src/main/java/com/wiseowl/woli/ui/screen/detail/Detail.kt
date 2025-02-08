package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val scrollState = rememberScrollState()
    val detailUseCase: DetailUseCase by inject(DetailUseCase::class.java)
    val viewModel = viewModel{ DetailViewModel(imageId, detailUseCase) }
    val state = viewModel.state.collectAsState()
    val detailState = if(state.value is DetailState.Success) state.value as DetailState.Success else null
    val complementaryColor = detailState?.detailModel?.complementaryColor?.let { Color(it) } ?: MaterialTheme.colorScheme.background
    Box(
        modifier
            .fillMaxSize()
            .background(complementaryColor)
            .verticalScroll(scrollState)
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
                        horizontalArrangement = Arrangement.Absolute.SpaceAround,
                    ) {
                        TextRoundButton(
                            modifier = Modifier.weight(1f),
                            text = "Preview",
                            backgroundColor = Color(it.detailModel.accentColor!!),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickImage) }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        TextRoundButton(
                            modifier = Modifier.weight(1f),
                            text = "Set",
                            backgroundColor = Color(it.detailModel.accentColor),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickSetWallpaper)}
                        )
                    }

                    it.detailModel.description?.let { it1 ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            text = it1,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 42.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(it.detailModel.accentColor!!)
                        )
                    }

                    it.detailModel.categories.let { categories ->
                        Column(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .background(Color(it.detailModel.accentColor!!))
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Category",
                                fontSize = 32.sp,
                                textAlign = TextAlign.Start,
                                lineHeight = 42.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(it.detailModel.complementaryColor!!)
                            )
                            LazyRow(modifier = Modifier.padding(top = 16.dp)) {
                                items(categories){ category ->
                                    TextRoundButton(
                                        modifier = Modifier.padding(end = 10.dp),
                                        text = category,
                                        backgroundColor = Color(it.detailModel.complementaryColor),
                                        textColor = Color(it.detailModel.accentColor),
                                        onClick = { }
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier.padding(top = 20.dp),
                                text = "Similar",
                                fontSize = 32.sp,
                                textAlign = TextAlign.Start,
                                lineHeight = 42.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(it.detailModel.complementaryColor)
                            )
                        }
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