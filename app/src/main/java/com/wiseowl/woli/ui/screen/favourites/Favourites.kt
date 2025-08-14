package com.wiseowl.woli.ui.screen.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import com.wiseowl.woli.ui.shared.component.AlertDialog
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Favourites(
    modifier: Modifier = Modifier
) {
    val accountUseCase: AccountUseCase by inject(AccountUseCase::class.java)
    val mediaUseCase: MediaUseCase by inject(MediaUseCase::class.java)
    val viewModel = viewModel { FavouritesViewModel(accountUseCase, mediaUseCase) }
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
                        onClick = { viewModel.onEvent(Action.Navigate(Screen.DETAIL, params = mapOf(
                            Screen.DETAIL.ARG_IMAGE_ID to image.id.toString()
                        ))) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            tint = Color.Green,
                            contentDescription = "remove from favourites",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                                .size(20.dp)
                                .clickable {
                                    viewModel.onEvent(FavouritesAction.OnClickFavouritesIcon(image.id))
                                },
                        )
                    }
                }
            }

            data.mediaIdForRemoveDialog?.let {
                if (data.showRemoveDialog) AlertDialog(
                    onDismissRequest = {viewModel.onEvent(FavouritesAction.OnClickDismissRemoveRequest)},
                    onConfirmation = {viewModel.onEvent(FavouritesAction.OnClickRemoveFromFavourites(data.mediaIdForRemoveDialog))},
                    dialogTitle = "Remove media from favourites",
                    dialogText = "Do you want to remove this media from favourites?",
                    icon = Icons.Default.Delete
                )
            }
        }
    }
}