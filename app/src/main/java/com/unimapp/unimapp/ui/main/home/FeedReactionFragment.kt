package com.unimapp.unimapp.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.unimapp.common.extensions.show
import com.unimapp.domain.entities.feed.AddFriendType
import com.unimapp.domain.entities.feed.FeedReactionType
import com.unimapp.uitoolkit.databinding.ReactionListItemBinding
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.uitoolkit.extensions.withSingleAdapter
import com.unimapp.unimapp.R
import com.unimapp.unimapp.databinding.FragmentFeedReactionBinding

class FeedReactionFragment : Fragment() {

    lateinit var viewmodel: HomePageViewModel
    lateinit var viewBinding: FragmentFeedReactionBinding

    companion object {
        private const val FEED_REACTION_TYPE = "feed_reaction_type"
        private const val FEED_ID = "feed_id"

        fun newInstance(reactionType: FeedReactionType, feedId: Long): FeedReactionFragment {
            val args = Bundle()
            args.putString(FEED_REACTION_TYPE, reactionType.name)
            args.putLong(FEED_ID, feedId)
            val fragment = FeedReactionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentFeedReactionBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity())[HomePageViewModel::class.java]
        arguments?.getString(FEED_REACTION_TYPE)?.let {
            val reactionList = viewmodel.getReactions(arguments?.getLong(FEED_ID) ?: 0L, FeedReactionType.valueOf(it))
            viewBinding.reactionList.withSingleAdapter(
                reactionList,
                bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                    ReactionListItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
                }, onBindView = { data ->
                    this.reactionUser.text = data.reactionUser
                    reactionType.setImageResource(data.reactionType.imageResource())
                    when (data.friendType) {
                        AddFriendType.IS_REQUEST_AVAILABLE -> this.sendRequestButton.show()
                        AddFriendType.IS_REQUESTED -> this.cancelRequestButton.show()
                    }
                    sendRequestButton.setOnClickListener {

                    }
                    cancelRequestButton.setOnClickListener {

                    }
                }, onItemClick = {

                }).addDivider(height = 1, color = ContextCompat.getColor(requireContext(), R.color.stroke_color_alpha20))
        }
    }

}

fun FeedReactionType.imageResource(): Int {
    return when (this) {
        FeedReactionType.STAR -> R.drawable.ic_reaction_star
        FeedReactionType.LOVE -> R.drawable.ic_reaction_love
        FeedReactionType.CRY -> R.drawable.ic_reaction_cry
        FeedReactionType.COOL -> R.drawable.ic_reaction_cool
        FeedReactionType.CELEBRATE -> R.drawable.ic_reaction_celebrate
        FeedReactionType.HAHA -> R.drawable.ic_reaction_haha
        FeedReactionType.OMG -> R.drawable.ic_reaction_omg
        FeedReactionType.ANGRY -> R.drawable.ic_reaction_angry
        else -> 0
    }
}
