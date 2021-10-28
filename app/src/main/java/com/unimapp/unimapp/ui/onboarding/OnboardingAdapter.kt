package com.unimapp.unimapp.ui.onboarding


import androidx.recyclerview.widget.RecyclerView
import com.unimapp.unimapp.ui.onboarding.OnboardingAdapter.OnboardingViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.unimapp.unimapp.R
import android.widget.TextView

class OnboardingAdapter(private val onBoardingItems: List<OnBoardingItem>) : RecyclerView.Adapter<OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private val imageOnboarding: ImageView = itemView.findViewById(R.id.imageOnboarding)
        fun bind(onBoardingItem: OnBoardingItem) {
            textTitle.text = onBoardingItem.title
            textDescription.text = onBoardingItem.description
            imageOnboarding.setImageResource(onBoardingItem.image)
        }
    }
}