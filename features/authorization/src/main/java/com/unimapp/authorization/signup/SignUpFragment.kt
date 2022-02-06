package com.unimapp.authorization.signup

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.unimapp.core.BaseFragment
import com.unimapp.authorization.R
import com.unimapp.authorization.databinding.FragmentSignUpBinding
import com.unimapp.common.extensions.*
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.simpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.simpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.OnTagClickListener
import com.unimapp.uitoolkit.tagview.TagItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding, SignUpState, Unit>() {

    override fun getViewModelClass() = SignUpViewModel::class.java

    private var selectedFaculty: SimpleSingleSelectorBottomSheet.Item? = null
    private var selectedYear: SimpleSingleSelectorBottomSheet.Item? = null
    private var selectedDegree: SimpleSingleSelectorBottomSheet.Item? = null
    private var selectedUniversity: SimpleSingleSelectorBottomSheet.Item? = null

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
        addTextChanges()
        acceptCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            validateContinueButton()
        }
    }

    private fun addTextChanges() {
        withBinding {
            firstName.onTextChanged {
                validateContinueButton()
            }
            lastname.onTextChanged {
                validateContinueButton()
            }
            email.onTextChanged {
                validateContinueButton()
            }
            birthdayInput.onTextChanged {
                validateContinueButton()
            }
        }
    }

    override fun onStateUpdate(state: SignUpState) {
        when (state) {
            is SignUpState.InitialValues -> {
                viewmodel.updateInitialValues()
            }
        }
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
                        onUniversitySelected(it)
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
                        onDegreeSelected(it)
                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun setStartYearSelector() {
        withBinding {
            startSelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.years
                    dialogTitle = getString(R.string.start_year)
                    onItemsSelected {
                        onYearSelected(it)
                    }
                }.show(childFragmentManager, SimpleSingleSelectorBottomSheet::class.java.canonicalName)
            }
        }
    }

    private fun setSpecialitySelector() {
        withBinding {
            specialitySelector.setOnClickListener {
                simpleSingleSelectorBottomSheet {
                    itemList = viewmodel.faculties
                    dialogTitle = getString(R.string.academic_degree)
                    onItemsSelected {
                        onFacultySelected(it)
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

    private fun validateContinueButton() {
        binding.continueButton.isEnabled = isAllFieldsValid()
    }

    private fun isAllFieldsValid(): Boolean {
        withBinding {
            val isFirstNameValid = !firstName.editText?.text.isNullOrEmpty()
            val isLastNameValid = !lastname.editText?.text.isNullOrEmpty()
            val isEmailValid = !email.editText?.text.isNullOrEmpty() && viewmodel.isEmailValid(email.editText?.text.toString())
            val isBirthdayValid = !birthdayInput.editText?.text.isNullOrEmpty()
            val isUniversitySelected = selectedUniversity.isNotNull()
            val isDegreeSelected = selectedDegree.isNotNull()
            val isYearSelected = selectedYear.isNotNull()
            val isFacultySelected = selectedFaculty.isNotNull()
            val isAgreementSelected = acceptCheck.isChecked
            return isFirstNameValid && isLastNameValid && isEmailValid && isBirthdayValid && isUniversitySelected
                    && isDegreeSelected && isYearSelected && isFacultySelected && isAgreementSelected
        }
    }

    private fun onUniversitySelected(item: SimpleSingleSelectorBottomSheet.Item) {
        selectedUniversity = item
        binding.unversitySelector.text = item.itemTitle
        validateContinueButton()
    }


    private fun onDegreeSelected(item: SimpleSingleSelectorBottomSheet.Item) {
        selectedDegree = item
        binding.degreeSelector.text = item.itemTitle
        validateContinueButton()
    }


    private fun onYearSelected(it: SimpleSingleSelectorBottomSheet.Item) {
        selectedYear = it
        binding.startSelector.text = it.itemTitle
        validateContinueButton()
    }


    private fun onFacultySelected(it: SimpleSingleSelectorBottomSheet.Item) {
        selectedFaculty = it
        binding.specialitySelector.text = it.itemTitle
        validateContinueButton()
    }

}


