package com.microsoft.research.karya.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.microsoft.research.karya.databinding.FragmentLeaderboardBinding
import com.microsoft.research.karya.ui.leaderboard.components.LeaderboardScreen
import com.microsoft.research.karya.utils.extensions.gone
import com.microsoft.research.karya.utils.extensions.observe
import com.microsoft.research.karya.utils.extensions.viewBinding
import com.microsoft.research.karya.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : Fragment() {
    private val binding by viewBinding(FragmentLeaderboardBinding::bind)
    private val viewModel by viewModels<LeaderboardViewModel>()

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply { composeView = this }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            LeaderboardScreen(
                viewModel = viewModel,
                languageCode = "EN",
                onNavigateBack = { findNavController().popBackStack() }
            ) {
            }
        }
    }

    private fun setupViews() {
        binding.appTb.setBackBtnClickListener {
            findNavController().popBackStack()
        }
        binding.leaderboardRv.adapter = LeaderboardListAdapter(emptyList())
    }

    private fun observeUiState() {
        with(binding) {
            viewModel.leaderboardList.observe(
                viewLifecycleOwner.lifecycle,
                lifecycleScope
            ) { lbList ->
                if (lbList.isEmpty()) {
                    leaderboardRv.gone()
                    emptyLeaderboardTv.visible()
                } else {
                    emptyLeaderboardTv.gone()
                    leaderboardRv.visible()
                    (leaderboardRv.adapter as LeaderboardListAdapter).updateList(lbList)
                }
            }
        }
    }
}
