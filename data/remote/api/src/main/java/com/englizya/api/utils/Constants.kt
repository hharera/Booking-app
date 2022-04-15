package com.englizya.api.utils

object Routing {
    const val REQUEST_PAYMENT = "${Domain.ENGLIZYA_PAY}api/payment/request-payment"
    const val SEARCH_TRIPS = "${Domain.ENGLIZYA_PAY}api/trip/search"
    const val GET_ALL_STATIONS = "${Domain.ENGLIZYA_PAY}api/station/all"
    const val GET_ALL_OFFICES: String = "${Domain.ENGLIZYA_PAY}api/office/all"
    const val GET_ALL_TRIPS = "${Domain.ENGLIZYA_PAY}api/trip/all"
    const val LOGIN = "${Domain.ENGLIZYA_PAY}login"
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

object Domain {
//    const val ENGLIZYA_PAY = "http://161.97.71.140:9100/"
    const val ENGLIZYA_PAY = "http://192.168.1.190:9100/"
}