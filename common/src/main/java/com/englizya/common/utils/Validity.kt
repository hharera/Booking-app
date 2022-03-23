package com.englizya.common.utils

class Validity {

    companion object {

        fun phoneNumberIsValid(string: String) =
            false
                .or(string.matches(Regex("^010[0-9]{8}\$")))
                .or(string.matches(Regex("^011[0-9]{8}\$")))
                .or(string.matches(Regex("^012[0-9]{8}\$")))
                .or(string.matches(Regex("^015[0-9]{8}\$")))

        fun cardNumberIsValid(card: String) =
            false
                .or(card.matches(Regex("^[0-9]{16}\$")))

        fun cardExpirationDataIsValid(card: String) =
            false
                .or(card.matches(Regex("^[0-9]{2}/[0-9]{2}\$")))

        fun cardCvvIsValid(card: String) =
            false
                .or(card.matches(Regex("^[0-9]{3}\$")))

        fun checkCode(string: String) =
            string.matches(Regex("^[0-9]{6}\$"))

        fun checkName(string: String) =
            string.matches(Regex("^[a-z]{5,15}\$"))

        fun checkDesc(string: String) =
            string.matches(Regex("^[a-z]{10,200}\$"))

        fun passwordIsValid(string: String) =
            string.matches(Regex("^[a-z]{5,25}\$"))

        fun checkDriverPassword(string: String) =
            string.matches(Regex("^[\\d]{4,40}\$"))

        fun checkCategoryName(value: String): Boolean =
            value.matches(Regex("^[a-z]{4,50}\$"))

        fun checkCodeValidity(code: String): Boolean =
            code.matches(Regex("^[0-9]{6}\$"))

        fun checkEmail(email: String): Boolean =
            email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))

        fun checkUsername(username: String): Boolean {
            return username.matches(Regex("^[\\d]{1,30}\$"))
        }

    }
}