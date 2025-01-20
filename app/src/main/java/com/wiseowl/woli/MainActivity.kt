package com.wiseowl.woli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventHandler
import com.wiseowl.woli.ui.navigation.Root
import com.wiseowl.woli.ui.theme.WoliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            EventHandler.subscribe { event ->
                when (event) {
                    is Event.Navigate -> navController.navigate(event.toRoute())
                    is Event.Progress -> Unit //TODO: show progress
                }
            }
            WoliTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Root(
                        modifier = Modifier,
                        innerPaddingValues = innerPadding,
                        navController = navController
                    )
                }
            }
        }
    }
}