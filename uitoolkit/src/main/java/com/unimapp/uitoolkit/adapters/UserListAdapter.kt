package com.unimapp.uitoolkit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.unimapp.common.extensions.show
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.uitoolkit.databinding.UserListItemBinding

class UserListAdapter : BaseAdapter<UserInfo, UserListAdapter.UserListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        return UserListHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class UserListHolder(private val userListItemBinding: UserListItemBinding) : RecyclerView.ViewHolder(userListItemBinding.root) {
        fun bind(userInfo: UserInfo) {
            with(userListItemBinding) {
                userImage.load(userInfo.userImage) {
                    listener(
                        onError = { _, e ->
                            e
                        }
                    )
                }
                userName.text = userInfo.userName
                userInfo.studyLevel?.let {
                    userInfoFieldOne.show()
                    dividerOne.show()
                    userInfoFieldOne.text = userInfo.studyLevel
                }
                userInfoFieldTwo.text = userInfo.userSpeciality
                userInfoFieldThree.text = userInfo.userUniversity
            }
        }
    }
}

data class UserInfo(
    val userName: String,
    val userImage: String,
    val userSpeciality: String,
    val userUniversity: String,
    val studyLevel: String? = null
)