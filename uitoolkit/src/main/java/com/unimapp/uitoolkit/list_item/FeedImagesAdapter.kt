package com.unimapp.uitoolkit.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.uitoolkit.databinding.FeedImageItemBinding

class FeedImagesAdapter : BaseAdapter<String, FeedImagesAdapter.FeedImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedImagesViewHolder {
        return FeedImagesViewHolder(FeedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FeedImagesViewHolder(val binding: FeedImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.imageViewMain.load(item)
        }
    }

}