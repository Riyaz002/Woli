package com.wiseowl.woli.ui.screen.collections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.ui.screen.collections.component.Collection
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Categories(
    modifier: Modifier = Modifier
) {
    val useCase: MediaUseCase by inject(MediaUseCase::class.java)
    val viewModel = viewModel{ CollectionsViewModel(useCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        data = state.value
    ) {
        Column {
            Text(
                text = "Collections",
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn (
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(it.categories.orEmpty(), key = { it.id }){
                    Collection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        title = it.title,
                        images = it.images,
                        onClick = { viewModel.onEvent(CollectionsAction.OnClickMedia(it) )}
                    )
                }

                item{
                    if(it.hasNext){
                        viewModel.onEvent(CollectionsAction.LoadPage(it.currentPage.plus(1)))
                        LoaderFooter(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}