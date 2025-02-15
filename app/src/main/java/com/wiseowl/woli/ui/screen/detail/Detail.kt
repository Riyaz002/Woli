package com.wiseowl.woli.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.detail.component.ChooserDialog
import com.wiseowl.woli.ui.screen.detail.component.ExpandableImageCard
import com.wiseowl.woli.ui.screen.detail.component.TextRoundButton
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.aspectRatio
import com.wiseowl.woli.ui.shared.component.Error
import com.wiseowl.woli.ui.shared.component.Shimmer
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
    val detailState = if(state.value is Result.Success) state.value as Result.Success else null
    val complementaryColor = detailState?.data?.complementaryColor?.let { Color(it) } ?: MaterialTheme.colorScheme.background
    val accent = detailState?.data?.accentColor?.let { Color(it) } ?: MaterialTheme.colorScheme.background
    Column(
        modifier.fillMaxSize().background(accent)
            .verticalScroll(scrollState)
    ) {
        when(val currentState = state.value){
            is Result.Loading -> ActionHandler.perform(Action.Progress(true))
            is Result.Success -> {
                ActionHandler.perform(Action.Progress(false))
                Column(
                    modifier = Modifier
                        .background(complementaryColor)
                        .padding(bottom = 20.dp)
                ) {
                    currentState.data.image.let { image ->
                        if (image == null) {
                            Shimmer(
                                modifier = Modifier
                                    .padding(top = 100.dp, start = 20.dp, end = 20.dp)
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                        } else{
                            ExpandableImageCard(
                                modifier = Modifier
                                    .padding(top = 100.dp, start = 20.dp, end = 20.dp),
                                image = image,
                                expanded = currentState.data.imagePreviewPopupVisible,
                                onDismiss = { viewModel.onEvent(DetailEvent.OnDismissImagePreview) },
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
                            backgroundColor = Color(currentState.data.accentColor!!),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickImage) }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        TextRoundButton(
                            modifier = Modifier.weight(1f),
                            text = "Set",
                            backgroundColor = Color(currentState.data.accentColor),
                            textColor = complementaryColor,
                            onClick = { viewModel.onEvent(DetailEvent.OnClickSetWallpaper)}
                        )
                    }

                    currentState.data.description?.let { it1 ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            text = it1,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 42.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(currentState.data.accentColor!!)
                        )
                    }
                }

                currentState.data.categories.let { categories ->
                    Column(
                        modifier = Modifier
                            .background(Color(currentState.data.accentColor!!))
                            .padding(20.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Category",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Start,
                            lineHeight = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(currentState.data.complementaryColor!!)
                        )
                        LazyRow(modifier = Modifier.padding(top = 16.dp)) {
                            items(categories){ category ->
                                TextRoundButton(
                                    modifier = Modifier.padding(end = 10.dp),
                                    text = category,
                                    backgroundColor = Color(currentState.data.complementaryColor),
                                    textColor = Color(currentState.data.accentColor),
                                    onClick = { viewModel.onEvent(DetailEvent.OnClickCategory(category)) }
                                )
                            }
                        }
                        currentState.data.similarImage.let { similarImage ->
                            if(similarImage.shimmer){
                                Shimmer(modifier = Modifier
                                    .width(100.dp)
                                    .height(50.dp)
                                    .padding(top = 20.dp)
                                    .clip(RoundedCornerShape(20.dp)))
                                Row {
                                    repeat(3){
                                        Shimmer(modifier = Modifier
                                            .padding(top = 16.dp)
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }
                                }
                            } else{
                                if(similarImage.images?.isNotEmpty() == true){
                                    Text(
                                        modifier = Modifier.padding(top = 20.dp),
                                        text = "Similar",
                                        fontSize = 32.sp,
                                        textAlign = TextAlign.Start,
                                        lineHeight = 42.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(currentState.data.complementaryColor)
                                    )
                                    LazyRow {
                                        similarImage.images.let { images ->
                                            items(images){ image ->
                                                ImageCard(
                                                    modifier = Modifier.size(100.dp),
                                                    image = image,
                                                    cornerRadius = 20.dp,
                                                    aspectRatio = 1f,
                                                    onClick = { viewModel.onEvent(DetailEvent.OnClickSimilarImage(image.id)) }
                                                )
                                                Spacer(modifier = Modifier.size(10.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(currentState.data.setWallpaperPopupVisible){
                    ChooserDialog(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        buttonColor = Color(currentState.data.accentColor!!),
                        backgroundColor = complementaryColor,
                        onEvent = viewModel::onEvent
                    )
                }
            }
            is Result.Error -> Error(modifier = modifier, error = currentState.error)
        }
    }
}