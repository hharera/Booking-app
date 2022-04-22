package com.englizya.profile

sealed class NavigationItem(
    val itemIconRes: Int,
    val itemTitleRes: Int,
) {
    object UserTickets : NavigationItem(R.drawable.ic_ticket, R.string.my_tickets)
    object DriverReview : NavigationItem(R.drawable.ic_star, R.string.review_driver)
    object PaymentHistory : NavigationItem(R.drawable.payments_history, R.string.payments_history)
    object PaymentCards : NavigationItem(R.drawable.ic_payment_card, R.string.payment_cards)
    object Settings : NavigationItem(R.drawable.ic_settings, R.string.settings)
    object SuggestIdea : NavigationItem(R.drawable.ic_bulb, R.string.suggest_idea)
    object ReportProblem : NavigationItem(R.drawable.ic_info, R.string.report_problem)
    object UpcomingFeatures :
        NavigationItem(R.drawable.ic_upcoming_features, R.string.upcoming_features)

    object AboutUs : NavigationItem(R.drawable.ic_info, R.string.about_us)

    object TermsAndPolicy :
        NavigationItem(R.drawable.ic_document, R.string.terms_and_privacy_policy)
}