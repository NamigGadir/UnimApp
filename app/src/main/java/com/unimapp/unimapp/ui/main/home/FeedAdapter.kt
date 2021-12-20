package com.unimapp.unimapp.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.pgreze.reactions.ReactionPopup
import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.entities.feed.FeedType
import com.unimapp.unimapp.R
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.uitoolkit.list_item.FeedImageView
import com.unimapp.uitoolkit.list_item.FeedResourceView
import com.unimapp.unimapp.databinding.FeedListMainItemBinding

class FeedAdapter(private val reactionPopup: ReactionPopup) : BaseAdapter<Feed, FeedAdapter.FeedViewHolder>() {

    private var mFeedAdapterActionListener: FeedAdapterActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedListMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position))
        reactionPopup.reactionSelectedListener = { reactionPosition ->
            mFeedAdapterActionListener?.onSelectedReaction(reactionPosition, position)
            true
        }
        holder.feedListMainItemBinding.reactionAction.setOnTouchListener(reactionPopup)
    }

    fun setFeedActionListener(feedListener: FeedAdapterActionListener) {

        mFeedAdapterActionListener = feedListener
    }

    inner class FeedViewHolder(val feedListMainItemBinding: FeedListMainItemBinding) : RecyclerView.ViewHolder(feedListMainItemBinding.root) {
        fun bind(feed: Feed) {
            val list = arrayListOf(R.drawable.ic_reaction_love, R.drawable.ic_reaction_cry, R.drawable.ic_reaction_star)
            feedListMainItemBinding.reactionView.setActions(list)
            feedListMainItemBinding.resourceLayout.removeAllViews()
            getResourceView(feed, feedListMainItemBinding.root.context)?.let {
                feedListMainItemBinding.resourceLayout.addView(it)
            }
        }

        private fun getResourceView(feed: Feed, context: Context): View? {
            return when (feed.feedType) {
                FeedType.MULTIPLE_IMAGE -> {
                    val view = FeedImageView(context)
                    view.setImages(
                        listOf(
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W1898505.jpg",
                            "https://yeniemlak.az/get-img/10122021W6898505.jpg"
                        )
                    )
                    view
                }
                FeedType.SINGLE_IMAGE -> {
                    val view = FeedImageView(context)
                    view.setImages(listOf("https://yeniemlak.az/get-img/10122021W6898505.jpg"))
                    view
                }
                FeedType.DOC -> {
                    val view = FeedResourceView(context)
                    view.setOnDownload { id, location ->
                        mFeedAdapterActionListener?.onDownloadImage(id, location)
                    }
                    view.setResource(FeedResourceView.FeedResource(1, "Myfile.pdf", "1.44 MB", "https://yeniemlak.az/get-img/10122021W6898505.jpg"))
                    view
                }
                else -> null
            }

        }
    }

    interface FeedAdapterActionListener {
        fun onReactionClicked()
        fun onSelectedReaction(reactionPosition: Int, feedId: Int)
        fun onDownloadImage(id: Long, location: String)
    }
}
