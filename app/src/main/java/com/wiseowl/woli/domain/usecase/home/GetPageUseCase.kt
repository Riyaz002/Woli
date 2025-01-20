package com.wiseowl.woli.domain.usecase.home

import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Page

class GetPageUseCase(private val repository: WoliRepository) {
    suspend operator fun invoke(pageNo: Int): Page {
        return Page(pageNo, repository.getPage(pageNo))
    }
}