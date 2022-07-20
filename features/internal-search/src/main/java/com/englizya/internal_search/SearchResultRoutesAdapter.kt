package com.englizya.internal_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.internal_search.databinding.RouteListItemBinding
import com.englizya.model.model.InternalRoutes
import com.englizya.model.model.RouteStations

class SearchResultRoutesAdapter(
    private var routes: List<InternalRoutes>,

    ) : RecyclerView.Adapter<SearchResultRoutesAdapter.RoutesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoutesViewHolder {
        val binding = RouteListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoutesViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
        holder.updateUI(routes[position])
    }

    fun setRoutes(list: List<InternalRoutes>) {

        routes = list

        notifyDataSetChanged()

    }

    inner class RoutesViewHolder(private val binding: RouteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun updateUI(route: InternalRoutes) {
            updateStationsUI(route.routeStations)
            binding.lineTitleTxt.text = route.routeName
            binding.lineCodeTxt.text = route.lineCode
        }

        private fun updateStationsUI(routeStations: List<RouteStations>) {
            SearchResultStationsAdapter(
                routeStations
            ).let {
                it.setStations(routeStations)
            }

        }

    }

    override fun getItemCount(): Int {
        Log.d("routesSize", routes.size.toString())
        return routes.size
    }
}