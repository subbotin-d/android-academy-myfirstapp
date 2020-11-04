package ru.subbotind.android.academy.myfirstapp.ui

import android.content.Intent
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

        binding.welcomeTextView.text =
            resources.getString(R.string.welcome_to_app_two_text_template, name)
        binding.closeButton.setOnClickListener { closeActivity() }
        binding.moveToSpecialActivity.setOnClickListener { moveToSpecialActivity() }
    }

    private fun moveToSpecialActivity() {
        val intent = Intent(this, SpecialActivity::class.java)
        startActivity(intent)
    }

    private fun closeActivity() {
        finish()
    }
}