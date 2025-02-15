package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.NavigationItem

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem>
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach {
            Column(Modifier.clickable { ActionHandler.perform(it.action) }.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(imageVector = it.icon, contentDescription = it.title)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = it.title)
            }
        }
    }
}