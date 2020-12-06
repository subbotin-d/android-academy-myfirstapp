package ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.replace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.FragmentFirstBinding
import ru.subbotind.android.academy.myfirstapp.ui.animationtask.task2.SecondFragment
import ru.subbotind.android.academy.myfirstapp.ui.extensions.setOnDebouncedClickListener

class FirstWithReplaceFragment : Fragment() {

    companion object {
        fun newInstance() = FirstWithReplaceFragment()
        private const val ANIMATION_DURATION = 600L
        const val SHARED_VIEW_ID = "task2:star"
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding: FragmentFirstBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left).apply {
            duration = ANIMATION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater)

        ViewCompat.setTransitionName(binding.fragmentOneImage, SHARED_VIEW_ID)

        binding.openNextFragmentButton.setOnDebouncedClickListener {
            routeToNextScreen()
        }

        return binding.root
    }

    private fun routeToNextScreen() {
        fragmentManager?.beginTransaction()
            ?.setReorderingAllowed(true)
            ?.addSharedElement(binding.fragmentOneImage, SecondFragment.SHARED_VIEW_ID)
            ?.replace(
                R.id.fragmentContainer,
                SecondFragment.newInstance(),
                SecondFragment::class.java.simpleName
            )
            ?.addToBackStack(SecondFragment::class.java.simpleName)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}