package com.unimapp.uitoolkit.tagview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.gone
import com.unimapp.common.extensions.isNotNull
import com.unimapp.uitoolkit.R
import java.util.*

/**
 * Android TagView Widget
 * github https://github.com/kaedea/Android-Cloud-TagView-Plus.git
 * This class have changed by @Namig Gadirov
 */
class TagView : RelativeLayout {
    /**
     * tag list
     */
    private val mTags: MutableList<TagItem> = ArrayList()
    private var mInflater: LayoutInflater? = null
    private var mViewTreeObserber: ViewTreeObserver? = null

    /**
     * listener
     */
    private var mClickListener: OnTagClickListener? = null

    /**
     * view size param
     */
    private var mWidth = 44

    /**
     * layout initialize flag
     */
    private var mInitialized = false

    /**
     * custom layout param
     */
    var lineMargin = 0
    var tagMargin = 0
    var textPaddingLeft = 0
    var textPaddingRight = 0
    var textPaddingTop = 0
    var texPaddingBottom = 0

    /**
     * constructor
     *
     * @param ctx
     */
    constructor(ctx: Context) : super(ctx, null) {
        initialize(ctx, null, 0)
    }

    /**
     * constructor
     *
     * @param ctx
     * @param attrs
     */
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs) {
        initialize(ctx, attrs, 0)
    }

    /**
     * constructor
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    constructor(ctx: Context, attrs: AttributeSet?, defStyle: Int) : super(ctx, attrs, defStyle) {
        initialize(ctx, attrs, defStyle)
    }

    /**
     * initalize instance
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    private fun initialize(ctx: Context, attrs: AttributeSet?, defStyle: Int) {
        mInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mViewTreeObserber = viewTreeObserver
        mViewTreeObserber?.addOnGlobalLayoutListener {
            if (!mInitialized) {
                mInitialized = true
                drawTags()
            }
        }

        // get AttributeSet
        val typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, defStyle)
        lineMargin = typeArray.getDimension(R.styleable.TagView_lineMargin, TagConstants.DEFAULT_LINE_MARGIN.toFloat()).toInt()
        tagMargin = typeArray.getDimension(R.styleable.TagView_tagMargin, TagConstants.DEFAULT_TAG_MARGIN.toFloat()).toInt()
        textPaddingLeft = typeArray.getDimension(R.styleable.TagView_textPaddingLeft, TagConstants.DEFAULT_TAG_TEXT_PADDING_LEFT.toFloat()).toInt()
        textPaddingRight = typeArray.getDimension(R.styleable.TagView_textPaddingRight, TagConstants.DEFAULT_TAG_TEXT_PADDING_RIGHT.toFloat()).toInt()
        textPaddingTop = typeArray.getDimension(R.styleable.TagView_textPaddingTop, TagConstants.DEFAULT_TAG_TEXT_PADDING_TOP.toFloat()).toInt()
        texPaddingBottom = typeArray.getDimension(R.styleable.TagView_textPaddingBottom, TagConstants.DEFAULT_TAG_TEXT_PADDING_BOTTOM.toFloat()).toInt()
        typeArray.recycle()
    }

    /**
     * onSizeChanged
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        if (width <= 0) return
        mWidth = measuredWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTags()
    }

    /**
     * tag draw
     */
    private fun drawTags() {
        if (!mInitialized) {
            return
        }

        // clear all tag
        removeAllViews()

        // layout padding left & layout padding right
        var total = (paddingLeft + paddingRight).toFloat()
        var listIndex = 1 // List Index
        var indexBottom = 1 // The Tag to add below
        var indexHeader = 1 // The header tag of this line
        var tagPre: TagItem? = null
        for (item in mTags) {
            val position = listIndex - 1

            // inflate tag layout
            val tagLayout = mInflater!!.inflate(R.layout.tagview_item, null) as View
            tagLayout.id = listIndex

            // tag text
            val tagView = tagLayout.findViewById<View>(R.id.tv_tag_item_contain) as TextView
            val tagImage = tagLayout.findViewById<View>(R.id.tag_image) as ImageView

            tagView.text = item.text

            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.tagTextSize.toFloat())
            tagLayout.setOnClickListener {
                mClickListener?.onTagClick(item, position)
            }
            if (item.isSelected) {
                tagLayout.setBackgroundResource(item.selectedBackground)
                tagView.setTextColor(item.selectedTextBackgroundColor.asColorResource(context))
            } else {
                tagLayout.setBackgroundResource(item.background)
                tagView.setTextColor(item.tagTextColor.asColorResource(context))
            }
            val params = tagView.layoutParams as LinearLayout.LayoutParams
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom)
            tagView.layoutParams = params
            if (item.tagImage.isNotNull()) {
                item.tagImage?.let {
                    tagImage.setImageResource(it)
                }
            } else
                tagImage.gone()

            tagView.visibility = if (item.text.isNotNull()) View.VISIBLE else View.GONE
            val tagWidth = tagView.paint.measureText(item.text ?: "") + textPaddingLeft.dp + textPaddingLeft.dp
            val tagParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            tagParams.bottomMargin = lineMargin
            if (mWidth <= total + tagWidth + TagConstants.LAYOUT_WIDTH_OFFSET) {
                //need to add in new line
                tagParams.addRule(BELOW, indexBottom)
                // initialize total param (layout padding left & layout padding right)
                total = (paddingLeft + paddingRight).toFloat()
                indexBottom = listIndex
                indexHeader = listIndex
            } else {
                //no need to new line
                tagParams.addRule(ALIGN_TOP, indexHeader)
                //not header of the line
                if (listIndex != indexHeader) {
                    tagParams.addRule(RIGHT_OF, listIndex - 1)
                    tagParams.leftMargin = tagMargin
                    total += tagMargin.toFloat()
                    if (tagPre != null && tagPre.tagTextSize < item.tagTextSize) {
                        indexBottom = listIndex
                    }
                }
            }
            total += tagWidth
            addView(tagLayout, tagParams)
            tagPre = item
            listIndex++
        }
    }

    /**
     * @param tag
     */
    fun addTag(tag: TagItem) {
        mTags.add(tag)
        drawTags()
    }

    /**
     * @param tag
     */
    fun addTag(tag: TagItem, position: Int) {
        mTags.add(position, tag)
        drawTags()
    }


    fun addTags(tags: List<TagItem>) {
        for (item in tags) {
            addTag(item)
        }
    }

    /**
     * get tag list
     *
     * @return mTags TagObject List
     */
    val tags: List<TagItem>
        get() = mTags

    /**
     * remove tag
     *
     * @param position
     */
    fun remove(position: Int) {
        mTags.removeAt(position)
        drawTags()
    }

    fun removeAllTags() {
        mTags.clear()
        drawTags()
    }

    fun setLineMargin(lineMargin: Float) {
        this.lineMargin = lineMargin.dp
    }

    fun setTagMargin(tagMargin: Float) {
        this.tagMargin = tagMargin.dp
    }

    fun setTextPaddingLeft(textPaddingLeft: Float) {
        this.textPaddingLeft = textPaddingLeft.dp
    }

    fun setTextPaddingRight(textPaddingRight: Float) {
        this.textPaddingRight = textPaddingRight.dp
    }

    fun setTextPaddingTop(textPaddingTop: Float) {
        this.textPaddingTop = textPaddingTop.dp
    }

    fun setTexPaddingBottom(texPaddingBottom: Float) {
        this.texPaddingBottom = texPaddingBottom.dp
    }

    fun setSelected(position: Int, selected: Boolean) {
        resetSelectedTag()
        tags[position].isSelected = selected
        drawTags()
    }

    fun resetSelectedTag() {
        val currentSelectedTag = getSelectedTag()
        currentSelectedTag?.isSelected = false
    }

    fun getSelectedTag() = tags.find { it.isSelected }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    fun setOnTagClickListener(clickListener: OnTagClickListener?) {
        mClickListener = clickListener
    }
}


