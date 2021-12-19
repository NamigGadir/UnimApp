package com.unimapp.uitoolkit.reaction

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import coil.load
import com.unimapp.common.extensions.gone
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.LinkResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.ReactionViewBinding
import de.hdodenhof.circleimageview.CircleImageView

class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ReactionViewBinding = ReactionViewBinding.inflate(LayoutInflater.from(context), this, true)


    fun setActions(list: List<Int>) {
        list.getOrNull(0)?.let {
           binding.reactOne.setImageResource(it)
        } ?: run {
            binding.reactOne.gone()
        }
        list.getOrNull(1)?.let {
            binding.reactTwo.setImageResource(it)
        } ?: run {
            binding.reactTwo.gone()
        }
        list.getOrNull(2)?.let {
            binding.reactThree.setImageResource(it)
        } ?: run {
            binding.reactThree.gone()
        }
    }

}