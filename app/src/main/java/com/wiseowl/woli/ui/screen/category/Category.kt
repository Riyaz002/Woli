package com.wiseowl.woli.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.repository.CategoryRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.shared.component.Error
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Category(
    modifier: Modifier = Modifier,
    category: String
) {

    val viewModel = viewModel {
        val categoryRepository: CategoryRepository by inject(CategoryRepository::class.java)
        CategoryViewModel(category, categoryRepository)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when(val currentState = state.value){
        is Result.Loading -> Unit
        is Result.Success -> {
            Column(modifier) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 100.dp),
                    text = currentState.data.category,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                LazyVerticalGrid(
                    modifier = Modifier.padding(top = 10.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    currentState.data.images?.let { images ->
                        items(images){ image ->
                            ImageCard(
                                modifier = Modifier.padding(10.dp),
                                image = image,
                                aspectRatio = 0.6f,
                                onClick = { viewModel.onEvent(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to image.id.toString()))) }
                            )
                        }
                    }
                }
            }
        }
        is Result.Error -> Unit
    }
}