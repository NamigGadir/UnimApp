package com.unimapp.unimapp.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unimapp.domain.entities.profile.About
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.unimapp.databinding.ProfileAboutListItemBinding

class ProfileAdapter(private val aboutList: List<About>) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var mProfileActionListener: ProfileActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            ProfileAboutListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(aboutList[position])
    }

    fun setActionListener(profileActionListener: ProfileActionListener) {
        mProfileActionListener = profileActionListener
    }

    inner class ProfileViewHolder(val profileItemBinding: ProfileAboutListItemBinding) :
        RecyclerView.ViewHolder(profileItemBinding.root) {
        fun bind(about: About) {
            profileItemBinding.aboutTitle.text = about.aboutTitle
        }
    }

    interface ProfileActionListener {
    }

    override fun getItemCount(): Int {
        return aboutList.size
    }
}