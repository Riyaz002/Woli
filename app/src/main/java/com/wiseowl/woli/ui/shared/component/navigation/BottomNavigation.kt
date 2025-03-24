package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.NavigationItem
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase

@Preview
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier
        .height(78.dp)
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface),
    navigationItems: List<NavigationItem> = GetNavigationItemsUseCase().invoke()
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach {
            Column(
                Modifier
                    .clickable { ActionHandler.perform(it.action) }
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val tintColor = if (it.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    Image(imageVector = it.icon, contentDescription = it.title, colorFilter = ColorFilter.tint(tintColor))
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = it.title, color = tintColor)
                }
            }
        }
    }
}