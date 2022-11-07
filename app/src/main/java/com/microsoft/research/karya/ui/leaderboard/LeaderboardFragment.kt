package com.microsoft.research.karya.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.ui.base.BaseFragment
import com.microsoft.research.karya.ui.leaderboard.components.LeaderboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : BaseFragment() {
    private val viewModel by viewModels<LeaderboardViewModel>()

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply { composeView = this }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            KaryaTheme {
                LeaderboardScreen(
                    viewModel = viewModel,
                    languageCode = "EN",
                    onNavigateBack = { findNavController().popBackStack() }
                ) {
                    showLanguageUpdater()
                }
            }
        }
    }
}
