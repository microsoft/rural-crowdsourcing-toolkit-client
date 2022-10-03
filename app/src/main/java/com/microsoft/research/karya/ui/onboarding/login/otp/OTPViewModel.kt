@file:Suppress("NAME_SHADOWING")

package com.microsoft.research.karya.ui.onboarding.login.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.research.karya.data.manager.AuthManager
import com.microsoft.research.karya.data.repo.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel
@Inject
constructor(
    private val authManager: AuthManager,
    private val workerRepository: WorkerRepository,
) : ViewModel() {

    private val _otpUiState: MutableStateFlow<OTPUiState> = MutableStateFlow(OTPUiState.Initial)
    val otpUiState = _otpUiState.asStateFlow()

    private val _otpEffects: MutableSharedFlow<OTPEffects> = MutableSharedFlow()
    val otpEffects = _otpEffects.asSharedFlow()

    private var _phoneNumber: MutableStateFlow<String> = MutableStateFlow("...")
    var phoneNumber = _phoneNumber.asSharedFlow()

    /**
     * Get the phone number of the worker to replace in the text.
     */
    fun retrievePhoneNumber() {
        viewModelScope.launch {
            val worker = authManager.getLoggedInWorker()
            _phoneNumber.value = worker.phoneNumber ?: "..."
        }
    }

    fun resendOTP() {
        viewModelScope.launch {
            _otpUiState.value = OTPUiState.Loading

            // We updated the worker phone number during first otp call, let's reuse that
            val worker = authManager.getLoggedInWorker()
            checkNotNull(worker.phoneNumber)

            workerRepository
                .resendOTP(accessCode = worker.accessCode, phoneNumber = worker.phoneNumber)
                .onEach { _otpUiState.value = OTPUiState.Initial }
                .catch { throwable -> _otpUiState.value = OTPUiState.Error(throwable) }
                .collect()
        }
    }

    fun verifyOTP(otp: String) {
        viewModelScope.launch {
            _otpUiState.value = OTPUiState.Loading

            // We updated the worker phone number during first otp call, let's reuse that
            val worker = authManager.getLoggedInWorker()
            checkNotNull(worker.phoneNumber)

            workerRepository
                .verifyOTP(accessCode = worker.accessCode, phoneNumber = worker.phoneNumber, otp)
                .onEach { worker ->
                    authManager.startSession(worker.copy(isConsentProvided = true))
                    _otpUiState.value = OTPUiState.Success
                    if (worker.profile != null && !worker.profile.isJsonNull) {
                        _otpEffects.emit(OTPEffects.NavigateToHomeScreen)
                    } else {
                        _otpEffects.emit(OTPEffects.NavigateToProfile)
                    }
                }
                .catch { throwable -> _otpUiState.value = OTPUiState.Error(throwable) }
                .collect()
        }
    }
}