package com.unimapp.uitoolkit.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.unimapp.common.extensions.firstIndexOrNull
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.BottomSheetSingleSelectorBinding


class SimpleSingleSelectorBottomSheet(
    var itemList: List<Item>,
    var title: String,
    var onItemsSelected: ((Item) -> Unit)?,
) : BaseBottomSheetDialog() {

    private lateinit var binding: BottomSheetSingleSelectorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetSingleSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            this.dialogTitle.text = title
            simpleSelectorList.removeAllViews()
            itemList.forEach {  // add radio buttons dynamically
                simpleSelectorList.addView(getRadioButton(it))
            }
            itemList.firstIndexOrNull { //find selected button index
                it.isSelected
            }?.let { index -> //set selected button as checked
                (simpleSelectorList.getChildAt(index) as RadioButton).isChecked = true
            }
            simpleSelectorList.setOnCheckedChangeListener { _, checkedId -> // start to listen
                itemList[checkedId - 1].let { item ->
                    onItemsSelected?.invoke(item)
                    resetSelection(item)
                }
                dismiss()
            }
        }
    }

    private fun resetSelection(item: Item) {
        itemList.find { it.isSelected }?.apply {
            isSelected = false
        }
        itemList.find { item == it }?.apply {
            isSelected = !isSelected
        }
    }

    private fun getRadioButton(item: Item): RadioButton {
        val view: RadioButton = LayoutInflater.from(requireContext()).inflate(R.layout.unim_radio_button, null) as RadioButton
        view.layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT)
        view.text = item.itemTitle
        view.id = item.itemId.toInt()
        return view
    }

    class Builder {
        var itemList: List<Item>? = null
        var dialogTitle: String? = null
        private var onItemsSelected: ((Item) -> Unit)? = null

        fun itemList(itemList: () -> List<Item>?) {
            this.itemList = itemList()
        }

        fun dialogTitle(dialogTitle: () -> String?) {
            this.dialogTitle = dialogTitle()
        }

        fun onItemsSelected(onItemsSelected: (Item) -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun build() = SimpleSingleSelectorBottomSheet(
            itemList ?: arrayListOf(),
            dialogTitle ?: "",
            onItemsSelected
        )
    }

    data class Item(
        val itemId: Long,
        val itemTitle: String,
        var isSelected: Boolean
    )
}

fun simpleSingleSelectorBottomSheet(lambda: SimpleSingleSelectorBottomSheet.Builder.() -> Unit) =
    SimpleSingleSelectorBottomSheet.Builder().apply(lambda).build()
