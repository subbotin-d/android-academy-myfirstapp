package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
        const val SHARED_VIEW_ID = "task2:star"
        private const val ANIMATION_DURATION = 400L
        private const val SHARE_ANIMATION_DURATION = 400L
    }

    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding
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
        _binding = FragmentSecondBinding.inflate(inflater)

        ViewCompat.setTransitionName(binding.fragmentTwoImage, SHARED_VIEW_ID)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}