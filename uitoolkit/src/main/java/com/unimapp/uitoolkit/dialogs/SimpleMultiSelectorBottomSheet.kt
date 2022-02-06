package com.unimapp.uitoolkit.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.BottomSheetSimpleSelectorBinding
import com.unimapp.uitoolkit.databinding.SimpleSelectorItemBinding
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.uitoolkit.extensions.withSingleAdapter

class SimpleMultiSelectorBottomSheet(
    var itemList: List<Item>,
    var title: String,
    var onItemsSelected: (() -> Unit)?,
) : BaseBottomSheetDialog() {

    private lateinit var binding: BottomSheetSimpleSelectorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetSimpleSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            this.dialogTitle.text = title
            simpleSelectorList.withSingleAdapter(
                itemList,
                bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                    SimpleSelectorItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
                }, onBindView = { data ->
                    checkBox.text = data.itemTitle
                    checkBox.isChecked = data.isSelected
                    checkBox.setOnCheckedChangeListener { compoundButton, checked ->
                        data.isSelected = checked
                    }
                }, onItemClick = {

                }).addDivider(height = 4, color = ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onItemsSelected?.invoke()
    }

    class Builder {
        var itemList: List<Item>? = null
        var dialogTitle: String? = null
        var onItemsSelected: (() -> Unit)? = null

        inline fun itemList(itemList: () -> List<Item>?) {
            this.itemList = itemList()
        }

        inline fun dialogTitle(dialogTitle: () -> String?) {
            this.dialogTitle = dialogTitle()
        }

        fun onItemsSelected(onItemsSelected: () -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun build() = SimpleMultiSelectorBottomSheet(
            itemList ?: arrayListOf(),
            dialogTitle ?: "",
            onItemsSelected
        )
    }

    data class Item(
        val itemId: Int,
        val itemTitle: String,
        var isSelected: Boolean
    )
}

fun simpleMultiSelectorBottomSheet(lambda: SimpleMultiSelectorBottomSheet.Builder.() -> Unit) =
    SimpleMultiSelectorBottomSheet.Builder().apply(lambda).build()
