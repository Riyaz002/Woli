package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.NavigationItem
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.ui.screen.home.component.aspectRatio

@Preview
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem> = GetNavigationItemsUseCase().invoke()
) {
    Row(
        modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(5.dp)
                    .clickable { ActionHandler.perform(it.action) },
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(100.dp))
                        .background(if(it.selected) Color.Black else Color.White)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val tintColor = if (it.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    Image(modifier = Modifier.size(24.dp), imageVector = it.icon, contentDescription = it.title, colorFilter = ColorFilter.tint(tintColor))
                    Text(text = it.title, color = tintColor, fontSize = 11.sp)
                }
            }
        }
    }
}