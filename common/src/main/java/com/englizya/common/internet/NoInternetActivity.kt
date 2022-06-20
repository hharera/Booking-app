package com.englizya.common.internet

import android.os.Bundle
import com.englizya.common.R
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.network.ConnectionLiveData
import org.koin.android.ext.android.inject

class NoInternetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_internet)

        connectionLiveData.observe(this) {
            if (it) {
                finish()
            }
        }
    }

}