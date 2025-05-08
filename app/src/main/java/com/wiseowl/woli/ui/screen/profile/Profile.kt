package com.wiseowl.woli.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.screen.profile.component.Button
import com.wiseowl.woli.ui.screen.profile.model.ProfileAction
import com.wiseowl.woli.ui.shared.component.AlertDialog
import org.koin.java.KoinJavaComponent.inject

@Preview
@Composable
fun Profile(modifier: Modifier = Modifier) {
    val getUserProfileUseCase by inject<ProfileUseCase>(ProfileUseCase::class.java)
    val viewModel = viewModel{ ProfileViewModel(getUserProfileUseCase) }
    val alertDialog = viewModel.dialogEvent.collectAsStateWithLifecycle()

    Page(modifier = modifier.padding(
        top = 30.dp
    ), data = viewModel.state.collectAsStateWithLifecycle().value) { data ->

        if(alertDialog.value.getContentIfNotHandled()==true){
            AlertDialog(
                onDismissRequest = { viewModel.onEvent(ProfileAction.DismissDeleteDialog) },
                onConfirmation = { viewModel.onEvent(ProfileAction.ConfirmDeleteAccount) },
                dialogTitle = "Delete Account",
                dialogText = "Are you sure you want to delete your account? All account related data will also be deleted forever.",
                icon = Icons.Default.Delete
            )
        }

        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    contentDescription = "Profile Picture",
                    imageVector = Icons.Default.AccountCircle,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .fillMaxWidth()
                )
                Text(
                    text = buildAnnotatedString {
                        append(data.currentUser?.firstName)
                        append("\n")
                        append("\n")
                        withStyle(
                            SpanStyle(fontSize = 48.sp)
                        ) {
                            append(data.currentUser?.lastName?.first().toString().capitalize(Locale.current))
                            append(data.currentUser?.lastName?.substring(1))
                        }
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout"
            ) { viewModel.onEvent(Action.Logout) }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = "Delete Account"
            ) { viewModel.onEvent(ProfileAction.DeleteAccountRequest) }
        }
    }
}