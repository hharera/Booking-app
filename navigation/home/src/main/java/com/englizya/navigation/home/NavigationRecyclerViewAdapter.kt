package com.englizya.navigation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.client.ticket.navigation.home.databinding.CardViewNavigationItemBinding

class NavigationRecyclerViewAdapter(
    private val onItemClicked : (String) -> Unit,
) : RecyclerView.Adapter<NavigationRecyclerViewAdapter.NavigationItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class NavigationItemViewHolder(binding: CardViewNavigationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun updateUI() {

        }

    }
}
