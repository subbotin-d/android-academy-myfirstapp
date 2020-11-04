package ru.subbotind.android.academy.myfirstapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.subbotind.android.academy.myfirstapp.R

class MainActivity : AppCompatActivity() {

    private var name: String = ""

    private companion object {
        const val NAME_BUNDLE_KEY = "name_bundle_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
    }

    private fun initListeners() {
        setNameButton.setOnClickListener { setNameText() }
        goToNextActivity.setOnClickListener { navigateToNextActivity() }
    }

    private fun navigateToNextActivity() {
        Log.d("MAIN", "navigation started")
        if (name.isNotEmpty()) {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.NAME_KEY, name)
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.set_your_name_message), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setNameText() {
        nameText.apply {
            val textInputContent: Editable? = nameTextInputLayout.editText?.text
            if (!textInputContent.isNullOrEmpty() && textInputContent.isNotBlank()) {
                name = textInputContent.trim().toString()
                setName()
            } else {
                visibility = View.GONE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (name.isNotEmpty()) {
            outState.putString(NAME_BUNDLE_KEY, name)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedName = savedInstanceState.getString(NAME_BUNDLE_KEY)
        savedName?.let {
            name = savedName
            setName()
        }
    }

    private fun setName() {
        nameText.text = resources.getString(R.string.name_text_template, name)
        nameText.visibility = View.VISIBLE
    }
}