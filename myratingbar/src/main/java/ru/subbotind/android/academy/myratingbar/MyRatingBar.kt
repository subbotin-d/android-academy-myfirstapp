package ru.subbotind.android.academy.myratingbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.math.floor

class MyRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private companion object {
        const val DEFAULT_MAX_STAR: Int = 5
        const val DEFAULT_CURRENT_RATING: Float = 0f
        const val DEFAULT_PADDING: Int = 0
    }

    private val starList: MutableList<RatingItemView> = mutableListOf()
    private var maxStar: Int = 0
    private var currentRating: Float = 0f
    private var padding: Int = 0
    private var isIndicator: Boolean = false

    @DrawableRes
    private var activeDrawableRes: Int

    @DrawableRes
    private var inactiveDrawableRes: Int

    init {
        val typedArray =
            this.context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar, defStyleAttr, 0)
        try {
            maxStar = typedArray.getInt(R.styleable.MyRatingBar_mrb_maxStar, DEFAULT_MAX_STAR)

            currentRating = typedArray.getFloat(
                R.styleable.MyRatingBar_mrb_currentRating,
                DEFAULT_CURRENT_RATING
            )

            padding = typedArray.getDimensionPixelSize(
                R.styleable.MyRatingBar_mrb_ratingBetweenPadding,
                DEFAULT_PADDING
            )

            isIndicator = typedArray.getBoolean(R.styleable.MyRatingBar_mrb_isIndicator, false)

            activeDrawableRes = typedArray.getResourceId(
                R.styleable.MyRatingBar_mrb_drawableActive,
                android.R.drawable.star_big_on
            )

            inactiveDrawableRes = typedArray.getResourceId(
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
            val ratingItemView = RatingItemView(this.context)

            val activeDrawable =
                ResourcesCompat.getDrawable(resources, this.activeDrawableRes, context.theme)

            val inactiveDrawable =
                ResourcesCompat.getDrawable(resources, this.inactiveDrawableRes, context.theme)

            ratingItemView.setDrawableEmpty(inactiveDrawable!!)
            ratingItemView.setDrawableActive(activeDrawable!!)

            if (index != maxStar - 1) {
                ratingItemView.setPadding(0, 0, padding, 0)
            }

            starList.add(ratingItemView)
            addView(ratingItemView, index)
        }
    }

    private fun updateRating() {
        if (currentRating < 0) {
            currentRating = 0f
        }

        if (currentRating > maxStar) {
            currentRating = maxStar.toFloat()
        }

        val fullNumberOfRating: Int = floor(currentRating / 1).toInt()
        val remainPartOfRating: Float = (currentRating) % 1


        (0 until fullNumberOfRating).forEach { index ->
            starList[index].fillFull()
        }

        if (fullNumberOfRating <= maxStar - 1) {
            starList[fullNumberOfRating].fillPartially(remainPartOfRating)
        }

        if (fullNumberOfRating + 1 <= maxStar - 1) {
            (fullNumberOfRating + 1 until maxStar).forEach { index ->
                starList[index].fillEmpty()
            }
        }

        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isIndicator) {
            val x = event.x

            starList.forEachIndexed { index, ratingItem ->
                val leftEdge = ratingItem.left.toFloat()
                val rightEdge = ratingItem.right.toFloat()
                if (x in leftEdge..rightEdge) {
                    val rating = index + (x - leftEdge) / (rightEdge - leftEdge)
                    setCurrentRating(rating)
                    return@forEachIndexed
                }
            }
        }

        return super.onTouchEvent(event)
    }

    fun setCurrentRating(rating: Float) {
        currentRating = rating
        updateRating()
    }
}