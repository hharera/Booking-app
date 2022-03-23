package com.englizya.navigation.home

import com.englizya.client.ticket.navigation.home.R

sealed class NavigationItem(
    val itemIconRes: Int,
    val itemTitleRes: Int,
) {
    object PaymentHistory : NavigationItem(R.drawable.payments_history, R.string.payments_history)
}