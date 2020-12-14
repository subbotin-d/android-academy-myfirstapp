package ru.subbotind.android.academy.myratingbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

@SuppressLint("RtlHardcoded")
internal class RatingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var emptyImageView: AppCompatImageView
    private var activeImageView: AppCompatImageView
    private var step: Float = 1f

    init {
        val view = inflate(context, R.layout.rating_item, this)

        emptyImageView = view.findViewById(R.id.inactive_item)
        activeImageView = view.findViewById(R.id.active_item)

        emptyImageView.setImageResource(android.R.drawable.star_big_off)
        activeImageView.setImageResource(android.R.drawable.star_big_on)
    }

    fun fillPartially(value: Float) {
        val level = (value * 10000).toInt()
        activeImageView.setImageLevel(level)
        emptyImageView.setImageLevel(10000 - level)
        invalidate()
    }

    fun fillFull() {
        activeImageView.setImageLevel(10000)
        emptyImageView.setImageLevel(0)
    }

    fun fillEmpty() {
        activeImageView.setImageLevel(0)
        emptyImageView.setImageLevel(10000)
    }

    fun setStep(step: Float) {
        this.step = step
    }

    fun setDrawableActive(drawable: Drawable) {
        val emptyClipDrawable = ClipDrawable(
            drawable.constantState?.newDrawable(),
            Gravity.LEFT,
            ClipDrawable.HORIZONTAL
        )

        activeImageView.setImageDrawable(emptyClipDrawable)
    }

    fun setDrawableEmpty(drawable: Drawable) {
        val emptyClipDrawable = ClipDrawable(
            drawable.constantState?.newDrawable(),
            Gravity.RIGHT,
            ClipDrawable.HORIZONTAL
        )

        emptyImageView.setImageDrawable(emptyClipDrawable)
    }
}