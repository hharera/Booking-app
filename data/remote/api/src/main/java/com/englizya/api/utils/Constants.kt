package com.englizya.api.utils

import com.google.common.net.HttpHeaders

object Routing {
    /**
     * @Invoice
     *
     */
    const val REQUEST_INVOICE_PAYMENT_ORDER = "${Domain.ENGLIZYA_BUS_LOCAL}api/payment/invoice/request-payment"
    const val REQUEST_INVOICE_PAYMENT = "https://secure-egypt.paytabs.com/payment/new/invoice"

    const val REQUEST_RECHARGE = "${Domain.ENGLIZYA_BUS_REMOTE}api/wallet/request-recharge"
    const val RESERVE_WITH_WALLET = "${Domain.ENGLIZYA_BUS_REMOTE}api/wallet/book-seats"
    const val REQUEST_RESERVATION_WITH_FAWRY = "${Domain.ENGLIZYA_BUS_REMOTE}api/payment/fawry/request-payment"
    const val RECHARGE_BALANCE = "${Domain.ENGLIZYA_BUS_REMOTE}api/wallet/recharge"
    const val GET_BALANCE = "${Domain.ENGLIZYA_BUS_REMOTE}api/wallet/balance"

    const val POST_DRIVER_REVIEW = "${Domain.ENGLIZYA_BUS_REMOTE}api/support/review-driver"
    const val POST_COMPLAINT = "${Domain.ENGLIZYA_BUS_REMOTE}api/support/complaint"

    const val BOOK_SEATS = "${Domain.ENGLIZYA_BUS_REMOTE}api/payment/confirm-reservation"
    const val CONFIRM_RESERVATION = "${Domain.ENGLIZYA_BUS_REMOTE}api/payment/confirm-reservation"
    const val REQUEST_RESERVATION = "${Domain.ENGLIZYA_BUS_REMOTE}api/payment/request-reservation"
    const val SEARCH_TRIPS = "${Domain.ENGLIZYA_BUS_REMOTE}api/trip/search"

    const val SIGNUP = "${Domain.ENGLIZYA_BUS_REMOTE}signup"
    const val FETCH_USER = "${Domain.ENGLIZYA_BUS_REMOTE}api/profile"
    const val RESET_PASSWORD = "${Domain.ENGLIZYA_BUS_REMOTE}reset-password"
    const val LOGIN = "${Domain.ENGLIZYA_BUS_REMOTE}login"

    const val GET_ALL_STATIONS = "${Domain.ENGLIZYA_BUS_REMOTE}api/station/all"
    const val GET_ALL_OFFICES: String = "${Domain.ENGLIZYA_BUS_REMOTE}api/office/all"
    const val GET_ALL_TRIPS = "${Domain.ENGLIZYA_BUS_REMOTE}api/trip/all"

    const val GET_TICKETS = "${Domain.ENGLIZYA_BUS_REMOTE}api/ticket/all"
    const val GET_TICKET_DETAILS = "${Domain.ENGLIZYA_BUS_REMOTE}api/ticket/"
    const val CANCEL_TICKET  = "${Domain.ENGLIZYA_BUS_REMOTE}api/ticket"
    const val GET_EXTERNAL_LINES  = "${Domain.ENGLIZYA_BUS_REMOTE}api/route/all/path"
    const val GET_OFFERS  = "${Domain.ENGLIZYA_BUS_REMOTE}api/offers"
    const val GET_INTERNAL_LINES  = "${Domain.ENGLIZYA_BUS_REMOTE}api/route/short/all/path"
    const val GET_ANNOUNCEMENTS  = "${Domain.ENGLIZYA_BUS_REMOTE}api/announcements"


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

object TIME_OUT {
    const val MILLIS = (30 * 1000).toLong()
}

val HttpHeaders.BEARER
    get() = "Bearer"