package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentSecondWithBottomNavigationBinding

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
        const val SHARED_VIEW_ID = "task3:star"
        private const val ANIMATION_DURATION = 400L
        private const val SHARE_ANIMATION_DURATION = 400L
    }

    private var _binding: FragmentSecondWithBottomNavigationBinding? = null
    private val binding: FragmentSecondWithBottomNavigationBinding
        get() = _binding!!

    private var sharedImageView: AppCompatImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())

        sharedElementEnterTransition = inflater.inflateTransition(R.transition.move_up).apply {
            duration = SHARE_ANIMATION_DURATION
        }

        enterTransition = inflater.inflateTransition(R.transition.slide_right_with_fade_in).apply {
            duration = ANIMATION_DURATION
        }

        exitTransition = inflater.inflateTransition(R.transition.slide_right_with_fade_out).apply {
            duration = ANIMATION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondWithBottomNavigationBinding.inflate(inflater)

        sharedImageView = binding.fragmentTwoImageBn

        ViewCompat.setTransitionName(binding.fragmentTwoImageBn, SHARED_VIEW_ID)

        return binding.root
    }

    fun getSharedImage(): AppCompatImageView? = sharedImageView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}