package com.wiseowl.woli

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.ui.event.UnhandledActionException
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventListener
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.ui.navigation.Root
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.shared.component.CircularProgressBar
import com.wiseowl.woli.ui.navigation.BottomNavigation
import com.wiseowl.woli.ui.theme.AppTheme
import com.wiseowl.woli.util.DeepLinkParser
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {

    private val eventListener by inject<EventListener>(EventListener::class.java)
    private val accountRepository by inject<AccountRepository>(AccountRepository::class.java)
    private val activityRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var fullScreen by remember { mutableStateOf(false) }
            var progressVisible by remember { mutableStateOf(false) }
            val snackBarHostState = remember { SnackbarHostState() }
            val deepLinkParser = DeepLinkParser()
            val screen = deepLinkParser.getPage(intent).getOrNull() ?: Screen.HOME

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when(destination.route){
                    Screen.DETAIL.route -> fullScreen = true
                    else -> fullScreen = false
                }
            }

            ActionHandler.listen { action ->
                when (action) {
                    is Action.Navigate -> navController.navigate(action.toRoute())
                    is Action.Pop -> {
                        navController.popBackStack(action.screen.route, action.inclusive)
                    }
                    is Action.Progress -> progressVisible = action.show
                    is Action.StartActivity -> startActivity(action.intent)
                    is Action.SnackBar -> lifecycleScope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(action.text)
                    }
                    is Action.Logout -> {
                        lifecycleScope.launch {
                            accountRepository.signOut()
                            eventListener.pushEvent(Event.Logout)
                            navController.currentDestination?.route?.let { currentRoute ->
                                navController.popBackStack()
                                navController.navigate(currentRoute)
                            }
                        }
                    }
                    else -> throw UnhandledActionException(action)
                }
            }
            AppTheme(dynamicColor = false) {
                Scaffold { padding ->
                    Box(Modifier.fillMaxHeight()) {
                        Column {
                            AnimatedVisibility(
                               visible = !fullScreen
                            ) {
                                Box(Modifier
                                    .fillMaxWidth()
                                    .height(padding.calculateTopPadding()))
                            }
                            Root(
                                modifier = Modifier,
                                navController = navController,
                                startScreen = screen.route
                            )
                            AnimatedVisibility(
                                visible = !fullScreen,
                                enter = slideInVertically { h -> h },
                                exit = slideOutVertically { h -> h }
                            ) {
                                Box(Modifier
                                    .fillMaxWidth()
                                    .height(padding.calculateBottomPadding()))
                            }
                        }
                        SnackbarHost(
                            snackBarHostState,
                            modifier = Modifier.padding(top = 30.dp),
                            snackbar = { snackBarData ->
                                Snackbar(snackBarData, shape = RoundedCornerShape(20.dp))
                            }
                        )
                        if (progressVisible) CircularProgressBar(Modifier.fillMaxSize())
                        BottomNavigation(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = padding.calculateBottomPadding()),
                            navController
                        )
                    }
                }
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            activityRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}