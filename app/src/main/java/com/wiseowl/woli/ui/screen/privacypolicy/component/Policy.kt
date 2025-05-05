package com.wiseowl.woli.ui.screen.privacypolicy.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.model.Policy

@Composable
fun Policy(modifier: Modifier = Modifier, policy: Policy) {
    Column(modifier = modifier) {
        Text(policy.title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = policy.description,
            fontSize = 14.sp
        )
    }
}