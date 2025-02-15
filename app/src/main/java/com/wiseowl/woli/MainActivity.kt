package com.wiseowl.woli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.ui.navigation.Root
import com.wiseowl.woli.ui.shared.component.CircularProgressBar
import com.wiseowl.woli.ui.theme.WoliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var progressVisible by remember {
                mutableStateOf(false)
            }

            ActionHandler.listen { event ->
                when(event) {
                    is Action.Navigate -> navController.navigate(event.toRoute())
                    is Action.Progress -> progressVisible = event.show
                    is Action.StartActivity -> startActivity(event.intent)
                }
            }
            WoliTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { it
                    Root(
                        modifier = Modifier.safeContentPadding(),
                        navController = navController
                    )
                    if(progressVisible) CircularProgressBar(Modifier.fillMaxSize())
                }
            }
        }
    }
}