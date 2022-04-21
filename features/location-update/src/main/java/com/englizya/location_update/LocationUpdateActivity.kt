package com.englizya.location_update

import android.os.Bundle
import androidx.activity.viewModels
import com.englizya.common.base.BaseActivity
import com.englizya.location_update.databinding.ActivityLocationUpdateBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationUpdateActivity : BaseActivity() {

    private lateinit var binding: ActivityLocationUpdateBinding
    private val locationUpdateViewModel: LocationUpdateViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            locationUpdateViewModel.sendRandomLocation()
        }
    }
}