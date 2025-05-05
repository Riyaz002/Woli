package com.wiseowl.woli.domain.usecase.privacypolicy

import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Policy

class GetPrivacyPolicyUseCase(private val remoteAPIService: RemoteAPIService){
    suspend operator fun invoke(): List<Policy>?{
        return remoteAPIService.getPrivacyPolicyPage()
    }
}