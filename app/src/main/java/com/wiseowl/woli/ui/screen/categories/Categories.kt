package com.wiseowl.woli.ui.screen.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Categories(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Categories")
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {

        }
    }
}