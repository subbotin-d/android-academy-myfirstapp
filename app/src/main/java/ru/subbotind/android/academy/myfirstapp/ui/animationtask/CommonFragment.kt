package ru.subbotind.android.academy.myfirstapp.ui.animationtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentCommonBinding
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.add.FirstWithAddFragment
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.replace.FirstWithReplaceFragment
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task3.BottomNavigationFragment

class CommonFragment : Fragment() {

    companion object {
        fun newInstance() = CommonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCommonBinding.inflate(inflater)

        binding.task1Button.setOnClickListener {
            //Todo activity animation
        }

        binding.task2AddButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    FirstWithAddFragment.newInstance(),
                    FirstWithAddFragment.TAG
                )
                ?.addToBackStack(FirstWithAddFragment.TAG)
                ?.commit()
        }

        binding.task2ReplaceButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    FirstWithReplaceFragment.newInstance(),
                    FirstWithReplaceFragment.TAG
                )
                ?.addToBackStack(FirstWithReplaceFragment.TAG)
                ?.commit()
        }

        binding.task3Button.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentContainer,
                    BottomNavigationFragment.newInstance(),
                    BottomNavigationFragment.TAG
                )
                ?.addToBackStack(BottomNavigationFragment.TAG)
                ?.commit()
        }

        return binding.root
    }
}