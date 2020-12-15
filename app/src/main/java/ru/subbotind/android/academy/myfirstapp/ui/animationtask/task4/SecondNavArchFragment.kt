package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentSecondNavArchBinding

class SecondNavArchFragment : Fragment() {

    companion object {
        const val SHARED_VIEW_ID = "task4:star"
        private const val ANIMATION_DURATION = 400L
        private const val SHARE_ANIMATION_DURATION = 400L
    }

    private var _binding: FragmentSecondNavArchBinding? = null
    private val binding: FragmentSecondNavArchBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())

        sharedElementEnterTransition = inflater.inflateTransition(R.transition.move_up).apply {
            duration = SHARE_ANIMATION_DURATION
        }

        enterTransition = inflater.inflateTransition(R.transition.slide_right_with_fade_in).apply {
            duration = ANIMATION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondNavArchBinding.inflate(inflater)

        ViewCompat.setTransitionName(binding.fragmentTwoImage, SHARED_VIEW_ID)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}