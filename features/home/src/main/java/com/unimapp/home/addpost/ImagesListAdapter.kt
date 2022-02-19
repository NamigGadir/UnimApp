package com.unimapp.home.addpost

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.unimapp.common.extensions.addBorder
import com.unimapp.common.extensions.addCorners
import com.unimapp.common.extensions.dp
import com.unimapp.home.R
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.uitoolkit.databinding.AddPostImageListItemBinding

class ImagesListAdapter : BaseAdapter<Uri, ImagesListAdapter.ImagesLisViewHolder>(
    areContentsTheSame = { itemOne, itemTwo ->
        itemOne == itemTwo
    },
    areItemsTheSame = { itemOne, itemTwo ->
        itemOne == itemTwo
    },
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesLisViewHolder {
        return ImagesLisViewHolder(AddPostImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImagesLisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ImagesLisViewHolder(val binding: AddPostImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Uri) {
            binding.mainImage.load(item) {
                transformations(
                    RoundedCornersTransformation(24.dp.toFloat())
                )
            }
        }
    }
}