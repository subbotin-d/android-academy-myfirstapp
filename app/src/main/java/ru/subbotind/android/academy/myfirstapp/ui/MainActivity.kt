package ru.subbotind.android.academy.myfirstapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.CommonFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CommonFragment.newInstance())
                .commit()
        }
    }
}