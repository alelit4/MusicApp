package com.alexandra.musicapp.ui.catalog

import androidx.recyclerview.widget.DiffUtil
import com.alexandra.musicapp.domain.models.Artist

class CatalogDiffUtils(private val oldList: List<Artist>,
private val newList: List<Artist>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}