package com.wiseowl.woli

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.ui.event.UnhandledActionException
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventListener
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
    private val activityRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var progressVisible by remember { mutableStateOf(false) }
            val snackBarHostState = remember { SnackbarHostState() }
            val deepLinkParser = DeepLinkParser()
            val screen = deepLinkParser.getPage(intent).getOrNull() ?: if(Firebase.auth.currentUser!=null) Screen.HOME else Screen.LOGIN

            ActionHandler.listen { action ->
                when (action) {
                    is Action.Navigate -> navController.navigate(action.toRoute())
                    is Action.Progress -> progressVisible = action.show
                    is Action.StartActivity -> startActivity(action.intent)
                    is Action.SnackBar -> lifecycleScope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(action.text)
                    }
                    is Action.Logout -> {
                        Firebase.auth.signOut()
                        eventListener.pushEvent(Event.Logout)
                        navController.navigate(Screen.LOGIN.route)
                    }
                    else -> throw UnhandledActionException(action)
                }
            }
            AppTheme(dynamicColor = false) {
                Scaffold { padding ->
                    Box(Modifier.fillMaxHeight()) {
                        Root(
                            modifier = Modifier.padding(top = padding.calculateTopPadding()),
                            navController = navController,
                            startScreen = screen.route
                        )
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
                                .padding(bottom = padding.calculateBottomPadding()+16.dp),
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