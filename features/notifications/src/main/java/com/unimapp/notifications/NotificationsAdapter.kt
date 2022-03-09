package com.unimapp.notifications

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.unimapp.common.extensions.gone
import com.unimapp.domain.entities.notifications.ActionType
import com.unimapp.domain.entities.notifications.NotificationItem
import com.unimapp.domain.entities.notifications.NotificationTypes
import com.unimapp.notifications.databinding.ListItemFriendRequestBinding
import com.unimapp.notifications.databinding.ListItemSimpleNotificationBinding
import com.unimapp.uitoolkit.base.BaseAdapter
import java.util.*

class NotificationsAdapter(private val onFriendRequestAction: (NotificationItem, Boolean) -> Unit) : BaseAdapter<NotificationItem, RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SIMPLE = 0
        const val VIEW_TYPE_FRIEND = 1
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.itemType) {
            NotificationTypes.SIMPLE -> VIEW_TYPE_SIMPLE
            NotificationTypes.FRIEND -> VIEW_TYPE_FRIEND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SIMPLE -> SimpleNotificationViewHolder(ListItemSimpleNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_FRIEND -> FriendRequestViewHolder(ListItemFriendRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SimpleNotificationViewHolder(ListItemSimpleNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SimpleNotificationViewHolder)
            holder.bind(getItem(position))
        else if (holder is FriendRequestViewHolder)
            holder.bind(getItem(position))
    }

    inner class SimpleNotificationViewHolder(private val listItemSimpleNotificationBinding: ListItemSimpleNotificationBinding) :
        RecyclerView.ViewHolder(listItemSimpleNotificationBinding.root) {
        fun bind(notificationItem: NotificationItem) {
            val (finalTextSpan, userNameSpan) = setSpanString(notificationItem)
            finalTextSpan.setSpan(userNameSpan, 0, notificationItem.userName.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            listItemSimpleNotificationBinding.notificationTitle.text = finalTextSpan
            listItemSimpleNotificationBinding.profileImage.load(notificationItem.userImage)
        }

        private fun setSpanString(notificationItem: NotificationItem): Pair<SpannableString, StyleSpan> {
            val finalTextSpan = SpannableString(
                "${notificationItem.userName} " +
                        itemView.context.getString(
                            when (notificationItem.actionType) {
                                ActionType.LIKE -> R.string.liked_comment_item_text
                                ActionType.COMMENT -> R.string.commented_post_item_text
                                else -> R.string.empty
                            }
                        ) +
                        when (notificationItem.actionType) {
                            ActionType.LIKE -> notificationItem.actionType.name.lowercase(Locale.getDefault())
                            ActionType.COMMENT -> notificationItem.commentTitle
                            else -> ""
                        }

            )
            val userNameSpan = StyleSpan(Typeface.BOLD)
            return Pair(finalTextSpan, userNameSpan)
        }
    }

    inner class FriendRequestViewHolder(private val listItemFriendRequestBinding: ListItemFriendRequestBinding) :
        RecyclerView.ViewHolder(listItemFriendRequestBinding.root) {
        fun bind(notificationItem: NotificationItem) {
            with(listItemFriendRequestBinding) {
                userName.text = notificationItem.userName
                profileImage.load(notificationItem.userImage)
                studyLevel.gone()
                dividerOne.gone()
                specialityLevel.text = notificationItem.speciality
                university.text = notificationItem.university
                acceptButton.setOnClickListener {
                    onFriendRequestAction(notificationItem, true)
                }
                cancelButton.setOnClickListener {
                    onFriendRequestAction(notificationItem, false)
                }
            }
        }
    }
}