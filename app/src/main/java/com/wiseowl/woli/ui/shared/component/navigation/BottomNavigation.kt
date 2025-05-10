package com.wiseowl.woli.ui.shared.component.navigation

import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.home.component.aspectRatio
import org.koin.java.KoinJavaComponent.inject

@Preview
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    var navigationBarVisible by remember { mutableStateOf(false) }
    val navigationBarHeight = remember {
        mutableStateOf(0)
    }
    val navigationBarOffset = animateIntAsState(targetValue = if (navigationBarVisible) 0 else navigationBarHeight.value)
    val navigationBarAlpha = animateFloatAsState(targetValue = if (navigationBarVisible) 1f else 0f)

    val getNavigationItemsUseCase by inject<GetNavigationItemsUseCase>(GetNavigationItemsUseCase::class.java)
    val navigationData = remember { mutableStateOf(getNavigationItemsUseCase(Screen.HOME)) }
    val currentIndex = remember { mutableStateOf(0) }
    LaunchedEffect(key1 = navigationData.value.selectedScreen) {
        currentIndex.value =
            navigationData.value.navItems.indexOfFirst { it.screen == navigationData.value.selectedScreen }
                .plus(1)
    }
    LaunchedEffect(key1 = true) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navigationData.value = getNavigationItemsUseCase(
                when (destination.route) {
                    Screen.HOME.route -> Screen.HOME
                    Screen.CATEGORIES.route -> Screen.CATEGORIES
                    Screen.Profile.route -> Screen.Profile
                    else -> null
                }.also { if(it==null) navigationBarVisible = false else navigationBarVisible = true }
            )
        }
    }
    val itemSize = remember { mutableStateOf(0) }
    val offset = ((itemSize.value * currentIndex.value) - (itemSize.value / 2)).toFloat()
    val ballAnimation = if(itemSize.value!=0) animateFloatAsState(targetValue = offset, animationSpec = tween(400, easing = EaseOutBounce)) else null
    Row(
        modifier
            .offset(y = navigationBarOffset.value.pxToDp())
            .alpha(navigationBarAlpha.value)
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White)
            .onSizeChanged { navigationBarHeight.value = it.height }
            .drawBehind {
                drawIntoCanvas {
                    if (itemSize.value != 0 && ballAnimation?.value != null) {
                        drawCircle(
                            Color.Black, (size.minDimension / 2.0f) - 10, center = Offset(
                                x = ballAnimation.value, y = size.height / 2
                            )
                        )
                    }
                }
            }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationData.value.navItems.forEachIndexed { index, navigationItem ->
            val isSelected = currentIndex.value == index+1
            Column(
                Modifier
                    .onSizeChanged { itemSize.value = it.width }
                    .padding(5.dp)
                    .clickable {
                        ActionHandler.perform(Action.Navigate(navigationItem.screen))
                        currentIndex.value = index + 1
                    },
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
                    val tintColor = if(isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
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

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }