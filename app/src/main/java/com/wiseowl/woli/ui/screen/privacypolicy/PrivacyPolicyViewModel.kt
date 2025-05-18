package com.wiseowl.woli.ui.screen.privacypolicy

import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.privacypolicy.GetPrivacyPolicyUseCase
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.privacypolicy.model.PrivacyPolicyModel

class PrivacyPolicyViewModel(
    private val getPoliciesUseCase: GetPrivacyPolicyUseCase
): PageViewModel<PrivacyPolicyModel>({ PrivacyPolicyModel("PRIVACY & SECURITY POLICY", getPoliciesUseCase()) }) {



    override fun onEvent(action: Action) {

    }
}