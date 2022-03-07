package com.englizya.location_update

import android.os.Bundle
import androidx.activity.viewModels
import com.englizya.common.base.BaseActivity
import com.englizya.location_update.databinding.ActivityLocationUpdateBinding
import javax.inject.Inject

class LocationUpdateActivity : BaseActivity() {

    private lateinit var binding: ActivityLocationUpdateBinding
    private val locationUpdateViewModel: LocationUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            locationUpdateViewModel.sendRandomLocation()
        }
    }
}