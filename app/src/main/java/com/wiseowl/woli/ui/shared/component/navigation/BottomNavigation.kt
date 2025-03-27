package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onSizeChanged
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
    val itemSize = remember { mutableStateOf(0) }
    val ballAnimation =
        animateFloatAsState(targetValue = (itemSize.value.toFloat() / 2) * (0.5 * (-2 + (navigationItems
            .indexOfFirst { it.selected }
            .plus(1) * 4))).toFloat()
        )
    Row(
        modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White)
            .drawBehind {
                drawIntoCanvas {
                    drawCircle(
                        Color.Black,
                        (size.minDimension / 2.0f) - 10,
                        center = Offset(
                            x = ballAnimation.value,
                            y = size.height / 2
                        )
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach { navigationItem ->
            Column(
                Modifier
                    .onSizeChanged {
                        itemSize.value = it.width
                    }
                    .padding(5.dp)
                    .clickable { ActionHandler.perform(navigationItem.action) },
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(100.dp))
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val tintColor =
                        if (navigationItem.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.title,
                        colorFilter = ColorFilter.tint(tintColor)
                    )
                    Text(text = navigationItem.title, color = tintColor, fontSize = 11.sp)
                }
            }
        }
    }
}