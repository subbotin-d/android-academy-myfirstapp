package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {

    companion object {
        fun newInstance() = BottomNavigationFragment()
    }

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding: FragmentBottomNavigationBinding
        get() = _binding!!

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationBinding.inflate(inflater)

        if (savedInstanceState == null) {
            firstFragment = FirstFragment.newInstance()
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragmentWithNavigationContainer,
                    firstFragment!!,
                    FirstFragment::class.java.simpleName
                )
                ?.commit()
        } else {
            firstFragment =
                fragmentManager?.findFragmentByTag(FirstFragment::class.java.simpleName) as FirstFragment?

            secondFragment =
                fragmentManager?.findFragmentByTag(SecondFragment::class.java.simpleName) as SecondFragment?
        }

        binding.navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_move_to_first_fragment -> {
                    openFirstFragment()
                }

                R.id.action_move_to_second_fragment -> {
                    openSecondFragment()
                }
            }

            true
        }

        return binding.root
    }

    private fun openFirstFragment() {
        if (firstFragment == null) {
            firstFragment = FirstFragment.newInstance()
        }

        secondFragment?.getSharedImage()?.let { sharedImage ->
            fragmentManager?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.addSharedElement(sharedImage, FirstFragment.SHARED_VIEW_ID)
                ?.replace(
                    R.id.fragmentWithNavigationContainer,
                    firstFragment!!,
                    FirstFragment::class.java.simpleName
                )
                ?.commit()
        }
    }

    private fun openSecondFragment() {
        if (secondFragment == null) {
            secondFragment = SecondFragment.newInstance()
        }

        firstFragment?.getSharedImage()?.let { sharedImage ->
            fragmentManager?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.addSharedElement(sharedImage, SecondFragment.SHARED_VIEW_ID)
                ?.replace(
                    R.id.fragmentWithNavigationContainer,
                    secondFragment!!,
                    SecondFragment::class.java.simpleName
                )
                ?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        firstFragment = null
        secondFragment = null
    }
}