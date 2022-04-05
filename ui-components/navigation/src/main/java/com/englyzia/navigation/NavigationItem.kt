package com.englyzia.navigation

sealed class NavigationItem(
    val itemIconRes: Int,
    val itemTitleRes: Int,
) {
    object PaymentHistory : NavigationItem(R.drawable.payments_history, R.string.payments_history)
    object PaymentCards : NavigationItem(R.drawable.ic_payment_card, R.string.payment_cards)
    object ProfileSettings : NavigationItem(R.drawable.ic_id, R.string.profile_settings)
    object AppSettings : NavigationItem(R.drawable.ic_settings, R.string.app_settings)
    object SuggestIdea : NavigationItem(R.drawable.ic_bulb, R.string.suggest_idea)
    object ReportProblem : NavigationItem(R.drawable.ic_info, R.string.report_problem)
    object UpcomingFeatures :
        NavigationItem(R.drawable.ic_upcoming_features, R.string.upcoming_features)

    object AboutUs : NavigationItem(R.drawable.ic_info, R.string.about_us)

    object TermsAndPolicy :
        NavigationItem(R.drawable.ic_document, R.string.terms_and_privacy_policy)
}