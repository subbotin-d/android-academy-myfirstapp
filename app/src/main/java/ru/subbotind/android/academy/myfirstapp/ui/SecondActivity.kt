package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    companion object {
        const val NAME_KEY = "name_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME_KEY)

        binding.welcomeTextView.text = resources.getString(R.string.welcome_text_template, name)
        binding.closeButton.setOnClickListener { closeActivity() }
    }

    private fun closeActivity() {
        finish()
    }
}