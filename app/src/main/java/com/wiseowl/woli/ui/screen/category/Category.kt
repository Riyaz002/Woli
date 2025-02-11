package com.wiseowl.woli.ui.screen.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.repository.CategoryRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.home.HomeEvent
import com.wiseowl.woli.ui.screen.home.component.ImageCard
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
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = currentState.data.category,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                currentState.data.images?.let { images ->
                    items(images.size){ index ->
                        ImageCard(
                            modifier = Modifier.padding(10.dp),
                            image = images[index],
                            cornerRadius = 20.dp,
                            aspectRatio = 0.6f,
                            onClick = { viewModel.onEvent(HomeEvent.OnClickImage(images[index].id)) }
                        )
                    }
                }
            }
        }
        is Result.Error -> Unit
    }
}