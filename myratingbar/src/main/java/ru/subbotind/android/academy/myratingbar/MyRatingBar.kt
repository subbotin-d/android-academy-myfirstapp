package ru.subbotind.android.academy.myratingbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat

class MyRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private companion object {
        const val DEFAULT_MAX_STAR: Int = 5
        const val DEFAULT_CURRENT_RATING: Int = 0
        const val DEFAULT_PADDING: Int = 0
    }

    private val starList: MutableList<AppCompatImageView> = mutableListOf()
    private var maxStar: Int = 0
    private var currentRating: Int = 0
    private var padding: Int = 0
    private var isIndicator: Boolean = false

    @DrawableRes
    private var activeDrawable: Int

    @DrawableRes
    private var inactiveDrawable: Int

    init {
        val typedArray =
            this.context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar, defStyleAttr, 0)
        try {
            maxStar = typedArray.getInt(R.styleable.MyRatingBar_mrb_maxStar, DEFAULT_MAX_STAR)

            currentRating =
                typedArray.getInt(R.styleable.MyRatingBar_mrb_currentRating, DEFAULT_CURRENT_RATING)

            padding = typedArray.getDimensionPixelSize(
                R.styleable.MyRatingBar_mrb_ratingBetweenPadding,
                DEFAULT_PADDING
            )

            isIndicator = typedArray.getBoolean(R.styleable.MyRatingBar_mrb_isIndicator, false)

            activeDrawable = typedArray.getResourceId(
                R.styleable.MyRatingBar_mrb_drawableActive,
                android.R.drawable.star_big_on
            )

            inactiveDrawable = typedArray.getResourceId(
                R.styleable.MyRatingBar_mrb_drawableEmpty,
                android.R.drawable.star_big_off
            )
        } finally {
            typedArray.recycle()
        }

        createStars()
        updateRating()
    }

    private fun createStars() {
        (0 until maxStar).forEach { index ->
            val imageView = AppCompatImageView(this.context)
            imageView.setImageResource(inactiveDrawable)
            if (index != maxStar - 1) {
                imageView.setPadding(0, 0, padding, 0)
            }
            starList.add(imageView)
            addView(imageView, index)
        }
    }

    private fun updateRating() {
        (0 until currentRating).forEach { index ->
            starList[index].setImageResource(activeDrawable)
        }

        (currentRating until maxStar).forEach { index ->
            starList[index].setImageResource(inactiveDrawable)
        }
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isIndicator) {
            val x = event.x.toInt()

            starList.forEachIndexed { index, imageView ->
                if (x in (imageView.left..imageView.right)) {
                    setCurrentRating(index + 1)
                }
            }
        }

        return super.onTouchEvent(event)
    }

    fun setCurrentRating(rating: Int) {
        currentRating = rating
        updateRating()
    }
}