package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentFirstNavArchBinding
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class FirstNavArchFragment : Fragment() {

    companion object {
        private const val ANIMATION_DURATION = 600L
        const val SHARED_VIEW_ID = "task4:star"
    }

    private var _binding: FragmentFirstNavArchBinding? = null
    private val binding: FragmentFirstNavArchBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left).apply {
            duration = ANIMATION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstNavArchBinding.inflate(inflater)

        ViewCompat.setTransitionName(binding.fragmentOneImage, SHARED_VIEW_ID)

        binding.openNextFragmentButton.setOnDebouncedClickListener {
            routeToNextScreen()
        }

        return binding.root
    }

    private fun routeToNextScreen() {
        val extras = FragmentNavigatorExtras(binding.fragmentOneImage to SHARED_VIEW_ID)
        findNavController().navigate(
            R.id.action_firstNavArchFragment_to_secondNavArchFragment,
            null,
            null,
            extras
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}