package com.wiseowl.woli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.ui.navigation.Root
import com.wiseowl.woli.ui.shared.component.CircularProgressBar
import com.wiseowl.woli.ui.shared.component.navigation.BottomNavigation
import com.wiseowl.woli.ui.theme.WoliTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    val getNavigationItemsUseCase by inject<GetNavigationItemsUseCase>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var progressVisible by remember {
                mutableStateOf(false)
            }

            onBackPressedDispatcher.addCallback(
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        progressVisible = false
                    }
                }
            )

            ActionHandler.listen { event ->
                when(event) {
                    is Action.Navigate -> navController.navigate(event.toRoute())
                    is Action.Progress -> progressVisible = event.show
                    is Action.StartActivity -> startActivity(event.intent)
                }
            }
            WoliTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { it
                    Box(modifier = Modifier.fillMaxSize()) {
                        Root(
                            modifier = Modifier.padding(bottom = 28.dp),
                            navController = navController
                        )
                        BottomNavigation(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 80.dp, start = 20.dp, end = 20.dp),
                            navigationItems = getNavigationItemsUseCase(),
                        )
                    }
                    if(progressVisible) CircularProgressBar(Modifier.fillMaxSize())
                }
            }
        }
    }
}