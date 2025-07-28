package com.wiseowl.woli.ui.screen.privacypolicy.model

import com.wiseowl.woli.domain.model.Policy

data class PrivacyPolicyModel(
    val title: String,
    val policies: List<Policy>?
)