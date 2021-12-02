package com.unimapp.unimapp.ui.authorization.signup

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.showDatePicker
import com.unimapp.common.extensions.showToast
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.simpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.simpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.OnTagClickListener
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding, SignUpState>() {

    override fun getViewModelClass() = SignUpViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding
        get() = FragmentSignUpBinding::inflate

    override val onViewInit: FragmentSignUpBinding.() -> Unit = {
        email.setEndIconOnClickListener {
            showToast("Clicked")
        }
        initTags(viewmodel.getTagList())
        setUniversitySelector()
        setDegreeSelector()
        setStartYearSelector()
        setSpecialitySelector()
        birthdayInput.setEndIconOnClickListener {
            showBirthdaySelectCalendar()
        }
        setAcceptButton()
    }

    override fun onStateUpdate(state: SignUpState) {

    }

    private fun setAcceptButton() {
        val acceptTitle = "I accept "
        val privacyTitle = "privacy policy"
        val and = " and "
        val userAgreement = "user aggreement"
        val ofUnim = " of UNIM"
        val acceptStartIndex = acceptTitle.length
        val acceptEndIndex = acceptTitle.length + privacyTitle.length
        val userAgreementStartIndex = acceptTitle.length + privacyTitle.length + and.length
        val userAgreementEndIndex = acceptTitle.length + privacyTitle.length + and.length + userAgreement.length
        val agreementText = SpannableString("$acceptTitle$privacyTitle$and$userAgreement$ofUnim")
        val acceptClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = R.color.unim_main_color.asColorResource(requireContext())
                ds.isUnderlineText = true
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            }
        }
        val agreementClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = R.color.unim_main_color.asColorResource(requireContext())
                ds.isUnderlineText = true
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            }
        }

        agreementText.setSpan(acceptClickable, acceptStartIndex, acceptEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        agreementText.setSpan(agreementClickable, userAgreementStartIndex, userAgreementEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.acceptCheck.text = agreementText
        binding.acceptCheck.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initTags(tagList: List<TagItem>) {
        withBinding {
            interestTags.removeAllTags()
            interestTags.addTags(tagList)
            interestTags.setOnTagClickListener(object : OnTagClickListener {
                override fun onTagClick(tag: TagItem, position: Int) {
                    if (position == (tagList.size - 1))
                        showSelectTag()
                }
            })
        }
    }

    private fun setUniversitySelector() {
        withBinding {
            unversitySelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.universities
                    dialogTitle = getString(R.string.name_of_university)
                    onItemsSelected {

                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun setDegreeSelector() {
        withBinding {
            degreeSelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.degrees
                    dialogTitle = getString(R.string.academic_degree)
                    onItemsSelected {

                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun setStartYearSelector() {
        withBinding {
            startSelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.degrees
                    dialogTitle = getString(R.string.academic_degree)
                    onItemsSelected {

                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun setSpecialitySelector() {
        withBinding {
            specialitySelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.degrees
                    dialogTitle = getString(R.string.academic_degree)
                    onItemsSelected {

                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun showSelectTag() {
        simpleMultiSelectorBottomSheet {
            itemList = viewmodel.interests
            dialogTitle = getString(R.string.interests)
            onItemsSelected {
                initTags(viewmodel.getTagList())
            }
        }.show(childFragmentManager, SimpleMultiSelectorBottomSheet::class.java.canonicalName)
    }

    private fun showBirthdaySelectCalendar() {
        requireContext().showDatePicker { date ->
            binding.birthdayInput.editText?.setText(date)
        }
    }

}

