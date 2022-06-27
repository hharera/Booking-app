package com.englizya.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.home_screen.databinding.CardViewOfferBinding
import com.englizya.model.model.Offer
import com.englizya.model.model.Trip
import com.squareup.picasso.Picasso

class OfferAdapter(
    private var offers: List<Offer>,

    ) : RecyclerView.Adapter<OfferAdapter.NavigationItemViewHolder>() {

    inner class NavigationItemViewHolder(private val binding: CardViewOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(offer: Offer) {
          //  binding.offerTitle.setText(offer.offerTitle)
            Picasso.get().load(offer.offerImageUrl).into(binding.offerImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        val binding = CardViewOfferBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(offers[position])
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    fun setOffers(list: List<Offer>) {
        offers = (list)
        notifyDataSetChanged()
    }
}
