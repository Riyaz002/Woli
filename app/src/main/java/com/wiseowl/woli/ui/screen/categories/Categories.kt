package com.wiseowl.woli.ui.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
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
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.categories.CategoriesUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.categories.component.Category
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.screen.home.component.LoaderFooter
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Categories(
    modifier: Modifier = Modifier
) {
    val useCase: CategoriesUseCase by inject(CategoriesUseCase::class.java)
    val viewModel = viewModel{ CategoriesViewModel(useCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()

    Page(
        modifier = modifier,
        data = state.value
    ) {
        Column {
            Text(
                text = "Categories",
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(it.categories.orEmpty()){ category ->
                    Category(modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                        category = category,
                        onClick = {
                            viewModel.onEvent(
                                Action.Navigate(
                                    Screen.CATEGORY,
                                    mapOf(Screen.CATEGORY.ARG_CATEGORY to category.name)
                                )
                            )
                        }
                    )
                }
                item {
                    if(it.currentPage > 0){
                        viewModel.onEvent(CategoriesEvent.LoadPage(it.currentPage.minus(1)))
                        LoaderFooter(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}