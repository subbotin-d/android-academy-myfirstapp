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
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentFirstWithBottomNavigationBinding

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
        const val SHARED_VIEW_ID = "task3:star"
        private const val ANIMATION_DURATION = 600L
        private const val SHARE_ANIMATION_DURATION = 400L
    }

    private var _binding: FragmentFirstWithBottomNavigationBinding? = null
    private val binding: FragmentFirstWithBottomNavigationBinding
        get() = _binding!!

    private var sharedImageView: AppCompatImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())

        sharedElementEnterTransition = inflater.inflateTransition(R.transition.move_up).apply {
            duration = SHARE_ANIMATION_DURATION
        }

        enterTransition = inflater.inflateTransition(R.transition.slide_left).apply {
            duration = ANIMATION_DURATION
        }

        exitTransition = inflater.inflateTransition(R.transition.slide_left).apply {
            duration = ANIMATION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstWithBottomNavigationBinding.inflate(inflater)
        sharedImageView = binding.fragmentOneImageBn

        ViewCompat.setTransitionName(binding.fragmentOneImageBn, SHARED_VIEW_ID)

        return binding.root
    }

    fun getSharedImage(): AppCompatImageView? = sharedImageView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}