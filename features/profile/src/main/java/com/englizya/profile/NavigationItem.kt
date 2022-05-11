package com.englizya.profile

sealed class NavigationItem(
    val itemIconRes: Int,
    val itemTitleRes: Int,
) {
    object LogOut : NavigationItem(R.drawable.ic_sign_out, R.string.logout)
    object LogIn : NavigationItem(R.drawable.ic_sign_in, R.string.login)
    object UserTickets : NavigationItem(R.drawable.ic_ticket, R.string.my_tickets)
    object DriverReview : NavigationItem(R.drawable.ic_rate, R.string.review_driver)
    object ContactUs : NavigationItem(R.drawable.ic_phone_contact, R.string.contact_us)
    object PaymentHistory : NavigationItem(R.drawable.payments_history, R.string.payments_history)
    object PaymentCards : NavigationItem(R.drawable.ic_payment_card, R.string.payment_cards)
    object Settings : NavigationItem(R.drawable.ic_settings, R.string.settings)
    object SuggestIdea : NavigationItem(R.drawable.ic_bulb, R.string.suggest_idea)

    object SuggestionsAndComplaint :
        NavigationItem(R.drawable.ic_bulb, R.string.suggestions_and_complaints)

    object UpcomingFeatures :
        NavigationItem(R.drawable.ic_upcoming_features, R.string.upcoming_features)

    object AboutUs : NavigationItem(R.drawable.ic_info, R.string.about_us)

    object TermsAndConditions :
        NavigationItem(R.drawable.ic_document, R.string.terms_and_conditions)
}