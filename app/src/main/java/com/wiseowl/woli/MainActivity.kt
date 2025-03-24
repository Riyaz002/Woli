package com.wiseowl.woli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.event.UnhandledActionException
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.ui.navigation.Root
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.shared.component.CircularProgressBar
import com.wiseowl.woli.ui.shared.component.navigation.BottomNavigation
import com.wiseowl.woli.ui.theme.AppTheme
import kotlinx.coroutines.launch
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
            val snackBarHostState = remember {
                SnackbarHostState()
            }

            ActionHandler.listen { action ->
                when (action) {
                    is Action.Navigate -> navController.navigate(action.toRoute())
                    is Action.Progress -> progressVisible = action.show
                    is Action.StartActivity -> startActivity(action.intent)
                    is Action.SnackBar -> lifecycleScope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(action.text)
                    }

                    else -> throw UnhandledActionException(action)
                }
            }
            AppTheme(dynamicColor = false) {
                Scaffold { it ->
                    Box(Modifier) {
                        Root(
                            modifier = Modifier.padding(bottom = 28.dp),
                            navController = navController,
                            startScreen = if(Firebase.auth.currentUser!=null) Screen.HOME.route else Screen.REGISTRATION.route
                        )
                        BottomNavigation(
                            modifier = Modifier
                                .padding(bottom = it.calculateBottomPadding())
                                .align(Alignment.BottomCenter)
                                .height(78.dp)
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surface),
                            navigationItems = getNavigationItemsUseCase()
                        )
                        SnackbarHost(
                            snackBarHostState,
                            modifier = Modifier.padding(top = 30.dp),
                            snackbar = { snackBarData ->
                                Snackbar(snackBarData, shape = RoundedCornerShape(20.dp))
                            }
                        )
                    }
                }
                if (progressVisible) CircularProgressBar(Modifier.fillMaxSize())
            }
        }
    }
}