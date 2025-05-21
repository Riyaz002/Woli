package com.wiseowl.woli.ui.screen.privacypolicy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.privacypolicy.GetPrivacyPolicyUseCase
import com.wiseowl.woli.ui.configuration.Constant
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.screen.privacypolicy.component.Policy
import org.koin.java.KoinJavaComponent.inject

@Composable
fun PrivacyPolicy(modifier: Modifier = Modifier) {
    val getUserPoliciesUseCase by inject<GetPrivacyPolicyUseCase>(GetPrivacyPolicyUseCase::class.java)
    val viewModel = viewModel{ PrivacyPolicyViewModel(getUserPoliciesUseCase) }

    Screen(
        modifier = modifier,
        data = viewModel.state.collectAsStateWithLifecycle().value
    ) { data ->
        Column(modifier = modifier.padding(horizontal = Constant.DEFAULT_PADDING.dp)) {
            Text(data.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                data.policies?.forEach {
                    item(key = it.title) {
                        Policy(
                            modifier = Modifier.padding(top = 20.dp),
                            policy = it
                        )
                    }
                }
            }
        }
    }
}