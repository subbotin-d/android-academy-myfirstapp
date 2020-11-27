package ru.subbotind.android.academy.myfirstapp.extension

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import ru.subbotind.android.academy.myratingbar.MyRatingBar


@BindingAdapter("imageResource")
fun setImageResource(imageView: AppCompatImageView, @DrawableRes resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("imageDrawable")
fun setImageResource(imageView: AppCompatImageView, resource: Drawable) {
    imageView.setImageDrawable(resource)
}

@BindingAdapter("movieRating")
fun setRating(ratingBar: MyRatingBar, rating: Int) {
    ratingBar.setCurrentRating(rating)
}