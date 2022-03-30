package com.englizya.api.utils

object Routing {
    const val GET_ALL_OFFICES: String = "${Domain.ENGLIZYA_PAY}api/office/all"
    const val GET_ALL_TRIPS = "${Domain.ENGLIZYA_PAY}api/trip/all"
    const val LOGIN = "${Domain.ENGLIZYA_PAY}login"
    const val SIGNUP = "${Domain.ENGLIZYA_PAY}signup"
}

object Request {
    const val TIME_OUT = 5000.toLong()
}

object Parameters {
    const val ACCESS_TOKEN: String = "access_token"
    const val PID = "pid"
}

object LoginMethods {
    const val EMAIL = "email"
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
    const val ENGLIZYA_PAY = "http://161.97.71.140:9100/"
}