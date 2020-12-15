package ru.subbotind.android.academy.myfirstapp.ui.animationtask

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentCommonBinding
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task1.FirstActivity
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.add.FirstWithAddFragment
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.replace.FirstWithReplaceFragment
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task3.BottomNavigationFragment
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task4.NavArchActivity
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class CommonFragment : Fragment() {

    companion object {
        fun newInstance() = CommonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCommonBinding.inflate(inflater)

        binding.task1Button.setOnDebouncedClickListener {
            val intent = Intent(this.context, FirstActivity::class.java)
            startActivity(intent)
        }

        binding.task2AddButton.setOnDebouncedClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    FirstWithAddFragment.newInstance(),
                    FirstWithAddFragment::class.java.simpleName
                )
                ?.addToBackStack(FirstWithAddFragment::class.java.simpleName)
                ?.commit()
        }

        binding.task2ReplaceButton.setOnDebouncedClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    FirstWithReplaceFragment.newInstance(),
                    FirstWithReplaceFragment::class.java.simpleName
                )
                ?.addToBackStack(FirstWithReplaceFragment::class.java.simpleName)
                ?.commit()
        }

        binding.task3Button.setOnDebouncedClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    BottomNavigationFragment.newInstance(),
                    BottomNavigationFragment::class.java.simpleName
                )
                ?.addToBackStack(BottomNavigationFragment::class.java.simpleName)
                ?.commit()
        }

        binding.task4Button.setOnDebouncedClickListener {
            val intent = Intent(this.context, NavArchActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}