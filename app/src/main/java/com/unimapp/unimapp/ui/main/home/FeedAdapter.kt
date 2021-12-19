package com.unimapp.unimapp.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseAdapter
import com.unimapp.unimapp.databinding.FeedListMainItemBinding

class FeedAdapter : BaseAdapter<String, FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedListMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FeedViewHolder(private val feedListMainItemBinding: FeedListMainItemBinding) : RecyclerView.ViewHolder(feedListMainItemBinding.root) {
        fun bind(item: String) {
            val list = arrayListOf(R.drawable.ic_reaction_love, R.drawable.ic_reaction_cry, R.drawable.ic_reaction_star)
            feedListMainItemBinding.reactionView.setActions(list)
        }
    }
}