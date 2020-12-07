package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task1

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.ActivityFirstBinding
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class FirstActivity : AppCompatActivity() {

    private companion object {
        const val ANIMATION_DURATION = 600L
        const val SHARED_ITEM_ID = "task1:star"
    }

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)

        with(window) {
            allowEnterTransitionOverlap = true

            val inflater = TransitionInflater.from(this@FirstActivity)

            exitTransition = inflater.inflateTransition(R.transition.slide_left).apply {
                duration = ANIMATION_DURATION
            }
        }

        ViewCompat.setTransitionName(binding.fragmentOneImage, SHARED_ITEM_ID)

        binding.openNextActivityButton.setOnDebouncedClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val options =
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    binding.fragmentOneImage,
                    SHARED_ITEM_ID
                )

            startActivity(intent, options.toBundle())
        }

        setContentView(binding.root)
    }
}