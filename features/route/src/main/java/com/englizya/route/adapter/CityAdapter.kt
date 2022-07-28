package com.englizya.route.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.InternalRoutes
import com.englizya.route.databinding.CityItemBinding

class CityAdapter(
    private var cities: List<String>,
    private val onItemClicked: (String) -> Unit,
) : RecyclerView.Adapter<CityAdapter.CityItemViewHolder>() {
    inner class CityItemViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun updateUI(cityName: String) {
            binding.cityNameTxt.text = cityName

            setUpListener(cityName)


        }

        private fun setUpListener(cityName: String) {

            binding.root.setOnClickListener {
                Log.d("Clicking To Navigate" , "click")
                onItemClicked(cityName)
                it. findNavController().navigate(
                    NavigationUtils.getUriNavigation(
                        Domain.ENGLIZYA_PAY,
                        Destination.INTERNAL_ROUTES_DETAILS,
                        cityName
                    )
                )
            }

        }



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityAdapter.CityItemViewHolder {
        val binding = CityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.updateUI(cities[position])
    }

    override fun getItemCount(): Int {
        Log.d("city Size", cities.size.toString())
        return cities.size
    }

    fun setCities(list: List<InternalRoutes>) {

        cities = list.groupBy {
            it.cityName
        }.keys.toList()
        notifyDataSetChanged()
    }

}


