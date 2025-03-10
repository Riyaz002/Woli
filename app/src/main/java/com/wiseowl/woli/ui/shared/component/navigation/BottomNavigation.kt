package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.NavigationItem
import com.wiseowl.woli.ui.util.shadow

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem>
) {
    Row(
        modifier
            .shadow(5f, 5f, 150f, Color.Black)
            .clip(RoundedCornerShape(20.dp))
            .border(
                BorderStroke(2.dp, brush =
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.Black.copy(alpha = .15f)
                    ))
                ),
                shape = RoundedCornerShape(20.dp))
            .background(Color.White)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach {
            Column(
                Modifier.clickable { ActionHandler.perform(it.action) }.weight(1f)
                .padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(imageVector = it.icon, contentDescription = it.title)
            }
        }
    }
}