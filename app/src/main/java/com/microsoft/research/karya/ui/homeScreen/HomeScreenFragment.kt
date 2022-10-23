package com.microsoft.research.karya.ui.homeScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.microsoft.research.karya.BuildConfig
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.databinding.FragmentHomeScreenBinding
import com.microsoft.research.karya.ui.base.BaseFragment
import com.microsoft.research.karya.utils.extensions.observe
import com.microsoft.research.karya.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : BaseFragment(R.layout.fragment_home_screen) {

    private val binding by viewBinding(FragmentHomeScreenBinding::bind)
    private val viewModel by viewModels<HomeScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComposable()
        observeNavigationState()
    }

    private fun setupComposable() {
        binding.composeView.setContent {
            KaryaTheme {
                HomeScreen(viewModel, findNavController())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshXPPoints()
        viewModel.refreshTaskSummary()
        viewModel.refreshPerformanceSummary()
        viewModel.setEarningSummary()
    }

    private fun observeNavigationState() {
        // Payments navigation flow
        viewModel.navigationFlow.observe(
            viewLifecycleOwner.lifecycle,
            lifecycleScope
        ) { navigation ->
            // Return if payments is not enabled in current config
            if (!BuildConfig.PAYMENTS_ENABLED) {
                return@observe
            }
            val action =
                when (navigation) {
                    HomeScreenNavigation.PAYMENT_REGISTRATION -> HomeScreenFragmentDirections.actionHomeScreenToPaymentRegistration()
                    HomeScreenNavigation.PAYMENT_VERIFICATION -> HomeScreenFragmentDirections.actionHomeScreenToPaymentVerificationFragment()
                    HomeScreenNavigation.PAYMENT_DASHBOARD -> HomeScreenFragmentDirections.actionHomeScreenToPaymentDashboardFragment()
                    HomeScreenNavigation.PAYMENT_FAILURE -> HomeScreenFragmentDirections.actionGlobalPaymentFailureFragment()
                }

            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Log.e("DASHBOARD_NAV_ERROR", e.toString())
            }
        }
    }
}
