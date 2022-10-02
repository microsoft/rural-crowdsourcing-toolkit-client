package com.microsoft.research.karya.ui.onboarding.login.otp

sealed class OTPEffects {
    object NavigateToProfile : OTPEffects()
    object NavigateToHomeScreen : OTPEffects()
}
