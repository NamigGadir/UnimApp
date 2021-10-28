package com.unimapp.unimapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.unimapp.common.extensions.onClick
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentOnboardingBinding


class OnBoardingFragment : BaseFragment<BaseViewModel, FragmentOnboardingBinding>() {
    private var onboardingAdapter: OnboardingAdapter? = null

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnboardingBinding
        get() = FragmentOnboardingBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnboardingItem()
        binding.onboardingViewpager.adapter = onboardingAdapter
        setOnboadingIndicator()
        setCurrentOnboardingIndicators(0)
        binding.onboardingViewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)
                handleButtonAction(position)
            }
        })
        binding.buttonOnBoardingAction.onClick {
            if (binding.onboardingViewpager.currentItem + 1 < onboardingAdapter?.itemCount ?: 0) {
                binding.onboardingViewpager.currentItem = binding.onboardingViewpager.currentItem + 1
            } else {
                navigateToSingIn()
            }
        }
        binding.skipButton.onClick {
            navigateToSingIn()
        }
    }

    private fun handleButtonAction(position: Int) {
        if (position == (onboardingAdapter?.itemCount ?: 1) - 1)
            binding.buttonOnBoardingAction.setText(R.string.button_title_done)
    }

    fun navigateToSingIn() {
        findNavController().navigate(R.id.signInFragment)
        Toast.makeText(context, "Navigating", Toast.LENGTH_LONG).show()
    }

    private fun setOnboadingIndicator() {
        val indicators: Array<ImageView?> = arrayOfNulls(3)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding_nonselected))
            indicators[i]?.layoutParams = layoutParams
            binding.layoutOnboardingIndicators.addView(indicators[i])
        }
    }


    private fun setCurrentOnboardingIndicators(index: Int) {
        val childCount: Int = binding.layoutOnboardingIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutOnboardingIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding_selected))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding_nonselected))
            }
        }
    }

    private fun setOnboardingItem() {
        val onBoardingItems: ArrayList<OnBoardingItem> = ArrayList()
        val itemFastFood = OnBoardingItem()
        itemFastFood.title = getString(R.string.onboarding_first_screen_title)
        itemFastFood.description = getString(R.string.onboarding_first_screen_description)
        itemFastFood.image = R.drawable.ic_onboarding_first
        val itemPayOnline = OnBoardingItem()
        itemPayOnline.title = getString(R.string.onboarding_second_screen_title)
        itemPayOnline.description = getString(R.string.onboarding_second_screen_description)
        itemPayOnline.image = R.drawable.ic_onboarding_second
        val itemEatTogether = OnBoardingItem()
        itemEatTogether.title = getString(R.string.onboarding_third_screen_title)
        itemEatTogether.description = getString(R.string.onboarding_third_screen_description)
        itemEatTogether.image = R.drawable.ic_onboarding_third
        onBoardingItems.add(itemFastFood)
        onBoardingItems.add(itemPayOnline)
        onBoardingItems.add(itemEatTogether)
        onboardingAdapter = OnboardingAdapter(onBoardingItems)
    }

}