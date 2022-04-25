package com.englizya.api.utils

object Routing {
    const val POST_DRIVER_REVIEW = "${Domain.ENGLIZYA_PAY}api/support/review-driver"
    const val POST_COMPLAINT = "${Domain.ENGLIZYA_PAY}api/support/complaint"
    const val BOOK_SEATS = "${Domain.ENGLIZYA_PAY}api/payment/confirm-reservation"
    const val REQUEST_PAYMENT = "${Domain.ENGLIZYA_PAY}api/payment/request-payment"
    const val SEARCH_TRIPS = "${Domain.ENGLIZYA_PAY}api/trip/search"
    const val GET_ALL_STATIONS = "${Domain.ENGLIZYA_PAY}api/station/all"
    const val GET_ALL_OFFICES: String = "${Domain.ENGLIZYA_PAY}api/office/all"
    const val GET_ALL_TRIPS = "${Domain.ENGLIZYA_PAY}api/trip/all"
    const val LOGIN = "${Domain.ENGLIZYA_PAY}login"
    const val RESET_PASSWORD = "${Domain.ENGLIZYA_PAY}reset-password"
    const val FETCH_USER = "${Domain.ENGLIZYA_PAY}api/profile"
    const val SIGNUP = "${Domain.ENGLIZYA_PAY}signup"
}

object Request {
    const val TIME_OUT = 15000.toLong()
}

object Parameters {
    const val ACCESS_TOKEN: String = "access_token"
    const val PID = "pid"
}

object Header {
    const val BEARER = "Bearer"
}

object LoginMethods {
    const val FACEBOOK = "facebook"
    const val Google = "google"
}

object AuthenticationParameters {
    const val USERNAME = "username"
    const val BEARER = "Bearer"
}

object Constants {
    const val TOKEN = "token"
}

