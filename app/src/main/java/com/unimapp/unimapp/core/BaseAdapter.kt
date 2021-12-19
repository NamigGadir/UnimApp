package com.unimapp.unimapp.core

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item, ViewHolder : RecyclerView.ViewHolder>(
    val areItemsTheSame: ((oldItem: Item, newItem: Item) -> Boolean)? = null,
    val areContentsTheSame: ((oldItem: Item, newItem: Item) -> Boolean)? = null
) : ListAdapter<Item, ViewHolder>(object : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return areItemsTheSame?.invoke(oldItem, newItem) ?: true
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return areContentsTheSame?.invoke(oldItem, newItem) ?: true
    }
})
{

}