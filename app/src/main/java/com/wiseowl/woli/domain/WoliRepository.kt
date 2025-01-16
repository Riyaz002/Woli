package com.wiseowl.woli.domain

import com.wiseowl.woli.domain.model.Image

interface WoliRepository {
    fun getPage(page: Int): List<Image>?
}