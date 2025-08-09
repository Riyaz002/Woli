package com.wiseowl.woli.ui.screen.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.favourites.FavouritesUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.home.HomeAction
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import com.wiseowl.woli.ui.shared.component.BasicButton
import com.wiseowl.woli.ui.shared.component.BasicTextField
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Favourites(
    modifier: Modifier = Modifier
) {
    val favouritesUseCase: FavouritesUseCase by inject(FavouritesUseCase::class.java)
    val viewModel = viewModel { FavouritesViewModel(favouritesUseCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Screen(
        modifier.fillMaxSize(),
        data = state.value
    ) { data ->
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Favourites",
                fontSize = 24.sp
            )

            Spacer(Modifier.height(20.dp))

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(data.favourites, key = { it.id }) { image ->
                    ImageCard(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        image = image,
                        cornerRadius = 20.dp,
                        aspectRatio = 0.6f,
                        onClick = { viewModel.onEvent(HomeAction.OnClickImage(image.id)) }
                    ) {
                        Box(
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                                .background(Color.Blue)
                                .size(20.dp)
                                .clickable {
                                    viewModel.onEvent(Action.SnackBar("CLICKED"))
                                }
                        ) {

                        }
                    }
                }
//                items(
//                    1,
//                    span = { GridItemSpan(2) }
//                ) {
//                    viewModel.onEvent(HomeAction.LoadNextPage)
//                    LoaderFooter(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//                }
            }
        }
    }
}