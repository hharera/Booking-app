package com.englyzia.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englyzia.navigation.databinding.CardViewNavigationItemBinding

class NavigationAdapter(
    private val navigationItems: List<NavigationItem>,
    private val onItemClicked: (NavigationItem) -> Unit,
) : RecyclerView.Adapter<NavigationAdapter.NavigationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        val bind = CardViewNavigationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = bind)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(navigationItems.get(position))
        holder.itemView.setOnClickListener {
            onItemClicked(navigationItems.get(position))
        }
    }

    override fun getItemCount(): Int {
        return navigationItems.size
    }

    class NavigationItemViewHolder(private val binding: CardViewNavigationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun updateUI(navigationItem: NavigationItem) {
            binding.imageView9.setImageResource(navigationItem.itemIconRes)
            binding.itemId.setText(navigationItem.itemTitleRes)
        }

    }
}
