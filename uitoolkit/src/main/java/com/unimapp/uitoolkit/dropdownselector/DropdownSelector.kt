//package com.unimapp.uitoolkit.dropdownselector
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.View
//import android.widget.CheckBox
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import com.guavapay.ui_toolkit.R
//import com.guavapay.ui_toolkit.extensions.gone
//import com.guavapay.ui_toolkit.extensions.show
//import com.unimapp.uitoolkit.R
//
///**
// * This class is responsible for checkable item view
// */
//class DropdownSelector @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : ConstraintLayout(context, attrs, defStyleAttr) {
//
//    companion object {
//        const val SIMPLE = 0
//        const val SUBTITLED = 1
//        const val RIGHT_ICON = 2
//        const val RIGHT_MENU = 3
//    }
//
//
//    private var layout: View = inflate(context, R.layout.checkable_item, this)
//    private val title = layout.findViewById<CheckBox>(R.id.title)
//    private val subtitle = layout.findViewById<TextView>(R.id.subtitle)
//    private val rightIcon = layout.findViewById<ImageView>(R.id.rightIcon)
//    private val rightMenu = layout.findViewById<TextView>(R.id.rightMenu)
//    private var itemType: Int? = null
//    private var rightIconImage: Int? = null
//    private var titleText: String? = null
//    private var rightMenuText: String? = null
//    private var subTitleText: String? = null
//
//    init {
//        context.obtainStyledAttributes(attrs, R.styleable.CheckableItem, 0, 0).apply {
//            itemType = getInt(R.styleable.CheckableItem_item_type, 0)
//            rightIconImage = getResourceId(R.styleable.CheckableItem_right_icon, 0)
//            titleText = getString(R.styleable.CheckableItem_title)
//            subTitleText = getString(R.styleable.CheckableItem_subtitle)
//            rightMenuText = getString(R.styleable.CheckableItem_right_menu_title)
//            recycle()
//            initView()
//        }
//    }
//
//    private fun initView() {
//        setItemType(itemType)
//        setRightIcon(rightIconImage)
//        setTitle(titleText)
//        setSubTitle(subTitleText)
//        setRightMenuTitle(rightMenuText)
//    }
//
//    fun setChecked() {
//        title.isChecked = true
//    }
//
//    fun setUnchecked() {
//        title.isChecked = false
//    }
//
//    fun setChecked(checked: Boolean) {
//        title.isChecked = checked
//    }
//
//    fun setActive(active: Boolean) {
//        title.isEnabled = active
//    }
//
//    fun isChecked(): Boolean {
//        return title.isChecked
//    }
//
//    /**
//     * Sets checkbox title
//     * @param titleText
//     */
//    fun setTitle(titleText: String?) {
//        title.text = titleText
//    }
//
//    /**
//     * Sets item subtitle
//     * @param subtitleText
//     */
//    fun setSubTitle(subtitleText: String?) {
//        subtitle.text = subtitleText
//    }
//
//    /**
//     * Sets item right menu title
//     * @param title
//     */
//    fun setRightMenuTitle(title: String?) {
//        rightMenu.text = title
//    }
//
//    /**
//     * Sets item right icon image
//     * @param rightIconImage
//     */
//    fun setRightIcon(rightIconImage: Int?) {
//        rightIconImage?.let { imageId ->
//            rightIcon.setImageResource(imageId)
//        }
//    }
//
//    /**
//     * There are four type of checkable item
//     * @param itemType type of the checkable item. Can be one of them  SIMPLE ,SUBTITLED, RIGHT_ICON, RIGHT_MENU
//     */
//    fun setItemType(itemType: Int?) {
//        when (itemType) {
//            SIMPLE -> {
//                subtitle.gone()
//                rightIcon.gone()
//                rightMenu.gone()
//            }
//            SUBTITLED -> subtitle.show()
//            RIGHT_ICON -> rightIcon.show()
//            RIGHT_MENU -> rightMenu.show()
//        }
//    }
//
//    /**
//     * Sets listeners of checking and right icon clicking
//     * @param onChecked Int-> id of view, Boolean-> checking status, String-> Checkbox title
//     */
//    fun setListener(onChecked: ((Int, Boolean, String) -> Unit)? = null, onRightMenuClick: (() -> Unit)? = null) {
//        title.setOnCheckedChangeListener { _, isChecked ->
//            if (title.isPressed)
//                onChecked?.invoke(title.id, isChecked, title.text.toString())
//        }
//        rightMenu.setOnClickListener {
//            onRightMenuClick?.invoke()
//        }
//    }
//
//    fun setOnCheckedChangeListener(onChecked: ((Int, Boolean, String) -> Unit)? = null) {
//        title.setOnCheckedChangeListener { _, isChecked ->
//            onChecked?.invoke(title.id, isChecked, title.text.toString())
//        }
//    }
//}
//
