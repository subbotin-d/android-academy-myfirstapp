package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task1

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private companion object {
        const val SHARE_ANIMATION_DURATION = 400L
        const val ANIMATION_DURATION = 400L
        const val SHARED_ITEM_ID = "task1:star"
    }

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)

        ViewCompat.setTransitionName(binding.activityTwoImage, SHARED_ITEM_ID)

        with(window) {
            allowEnterTransitionOverlap = true

            val inflater = TransitionInflater.from(this@SecondActivity)

            sharedElementEnterTransition = inflater.inflateTransition(R.transition.move_up).apply {
                duration = SHARE_ANIMATION_DURATION
            }

            enterTransition =
                inflater.inflateTransition(R.transition.slide_right_with_fade_in).apply {
                    duration = ANIMATION_DURATION
                }
        }

        setContentView(binding.root)
    }
}