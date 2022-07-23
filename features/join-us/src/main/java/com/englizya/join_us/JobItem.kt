package com.englizya.join_us

sealed class JobItem(
    val itemIconRes: Int,
    val itemTitleRes: Int,
) {
    object Driver : JobItem(R.drawable.ic_blue_user, R.string.driver)

}