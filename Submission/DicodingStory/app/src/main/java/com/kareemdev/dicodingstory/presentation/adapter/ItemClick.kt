package com.kareemdev.dicodingstory.presentation.adapter

import com.kareemdev.dicodingstory.databinding.ItemStoryBinding
import com.kareemdev.dicodingstory.domain.model.ListStoryItem

interface ItemClick {
    fun onClick(binding: ItemStoryBinding, story: ListStoryItem)
}