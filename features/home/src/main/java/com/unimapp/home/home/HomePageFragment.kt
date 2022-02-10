package com.unimapp.home.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.pgreze.reactions.ReactionPopup
import com.github.pgreze.reactions.dsl.reactionConfig
import com.github.pgreze.reactions.dsl.reactions
import com.unimapp.common.extensions.showToast
import com.unimapp.core.BaseFragment
import com.unimapp.home.R
import com.unimapp.home.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : BaseFragment<HomePageViewModel, FragmentHomePageBinding, HomePageState, Unit>(), FeedAdapter.FeedAdapterActionListener {

    override fun getViewModelClass() = HomePageViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate


    private val popupReaction by lazy {
        val reactionStrings = arrayOf("like", "love", "laugh", "wow", "sad", "angry", "dsff", "sdfsdf", "sdfsdfsdf", "sdfsdfsdf")
        val config = reactionConfig(requireContext()) {
            reactions {
                resId { R.drawable.ic_reaction_star }
                resId { R.drawable.ic_reaction_love }
                resId { R.drawable.ic_reaction_celebrate }
                resId { R.drawable.ic_reaction_haha }
                resId { R.drawable.ic_reaction_cool }
                resId { R.drawable.ic_reaction_omg }
                resId { R.drawable.ic_reaction_cry }
                resId { R.drawable.ic_reaction_angry }
            }
            withReactionTexts { position ->
                reactionStrings[position]
            }
            popupAlpha = 255
            popupMargin = context.resources.getDimensionPixelSize(R.dimen._28sdp)
            reactionSize = context.resources.getDimensionPixelSize(R.dimen._28sdp)
            horizontalMargin = context.resources.getDimensionPixelSize(R.dimen._16sdp)
            withPopupColor(Color.WHITE)
        }
        ReactionPopup(requireContext(), config)
    }

    private val adapter by lazy { FeedAdapter(popupReaction) }

    override val onViewInit: FragmentHomePageBinding.() -> Unit = {
        feedList.adapter = adapter
        adapter.setFeedActionListener(this@HomePageFragment)
        viewmodel.loadFeed()
    }

    override fun onReactionClicked() {

    }

    override fun onSelectedReaction(reactionPosition: Int, feedId: Int) {
        showToast("$reactionPosition  $feedId")
    }

    override fun onDownloadImage(id: Long, location: String) {
        showToast(location)
    }

    override fun onCommentClick(id: Long) {
        findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToCommentsFragment())
    }

    override fun showReactions(id: Long) {
        feedReactionsBottomSheet {

        }.show(childFragmentManager, FeedReactionsBottomSheet::class.java.canonicalName)
    }

    override fun onStateUpdate(state: HomePageState) {
        when (state) {
            is HomePageState.FeedList -> {
                adapter.submitList(state.feedList)
            }
        }
    }
}